package spell;
import spell.ITrie;
import spell.TrieNode;
import java.util.*;
import java.io.*;
import java.lang.StringBuilder;
public class Trie implements ITrie {
  //      PRIVATE DATA MEMBERS      //
  private TrieNode root;
  private int wordCount;
  private int nodeCount;
  private String ID;
  public HashMap<Character, TrieNode> children;

  //CONSTRUCTORS
  public Trie() {
    this.root = new TrieNode();
    this.children = new HashMap<Character, TrieNode>();
    this.ID = UUID.randomUUID().toString();
  }
  //BUILDERS
  public void loadTrie(File dictionary) throws Exception{

      BufferedReader br = new BufferedReader(new FileReader(dictionary));
      String str = "";
      while ( (str = br.readLine()) != null ){
        try {
          String myString = (String) str;
        } catch(Exception e){
          e.printStackTrace();
          continue;
        }
        this.add(str);
        //System.out.println(this.toString());
      }
      br.close();

  }
  public void add(String word) {

    Integer position = 0;
    TrieNode currNode = this.root;
    String w = "";
    while(position < word.length()) {
      if( currNode.children.containsKey(word.charAt(position)) ) {
        if( position == word.length()-1 ){
          currNode = currNode.children.get( word.charAt(position) );
          currNode.count += 1;
          this.nodeCount += 1;
          w = new String();
          position++;
        }

        else {
          w += word.charAt(position);
          currNode = currNode.children.get(word.charAt(position));
          position++;
        }

      }

      else if (!currNode.children.containsKey(word.charAt(position))){

        currNode.children.put(word.charAt(position), new TrieNode(word.charAt(position)));
        //System.out.println(currNode.toString());
        currNode = currNode.children.get(word.charAt(position));

        if( position == word.length()-1 ){
          w += word.charAt(position);
          this.wordCount++;
          this.nodeCount++;
          position++;
          currNode.incrementCount();
          currNode.setWord(w);
          currNode.setEnd();
          currNode.count += 1;
          w = new String();
        }
        else {
         w += word.charAt(position);
         this.nodeCount++;
         position++;
        }

      }
    }
  }
  //TOOLS
  public int hashCode() {
   String str = this.ID;
   int hash = 0;
   for(Integer i = 0; i < str.length(); i++) {

     if (i % 2 == 0) {
       hash += str.charAt(i) * Math.pow(31.0, 1);
     }
     else {
       hash += str.charAt(i) * Math.pow(31.0, 2);
     }
    }

    return hash;

  }
  public String toString() {
    String str = "";
    for( Character c : this.root.children.keySet() ) {
      str += "\n" + c + " \n" + this.root.children.get(c).toString() + '\n';
    }
    return str;
  }
  public int getNodeCount(){
   return this.nodeCount;
  }
  public TrieNode getRoot() {
    return this.root;
  }
  public int getWordCount() {
   return this.wordCount;
  }
  public static String max(Trie t, ArrayList<String> list){
    //toReturn contains the string to return
    String toReturn = "";
    //count contains the frequency of the string
    Integer count = 0;
    //n contains the node to find
    TrieNode n = new TrieNode();
    // for the strings in the passed in list
    for(String item : list){
      n = t.find(item);
      //if the find was successful
      if (n != t.root){
        if (item.length() > 1){
          //if the count of the node is greater than count
          if(n.count > count){
            //set count to the node count to compare to other nodes
            count = n.count;
            //set the string to return to the word of the node
            toReturn = item;
          }
        }
        else{
          continue;
        }
      }
    }
    //return the most frequently occuring string
    return toReturn;

  }
  public TrieNode find(String str) {
   //traverse by character
   TrieNode current = new TrieNode();
   current = this.root;
   Integer position = 0;
   while ( position < str.length() ) {
    //if keyError, return root
     if(!current.children.containsKey(str.charAt(position))){
         return this.root;
      }
      else {
        current = current.children.get(str.charAt(position));
        position++;
      }
    }
    if(current.getWord() == null){
      return this.root;
    }
    return current;

  }



///     EDIT DISTANCE     ///
  public String findClosestEditDistance(String word){
    ArrayList<String> results = new ArrayList<String>();
    Integer null_position = 0;
    results.add(getDeletionDistance(word));
    results.add(getTranspositionDistance(word));
    results.add(getAlterationDistance(word));
    results.add(getInsertionDistance(word));
    if(results.size() == 0){
      return "";
    }
    return max(this, results);
  }

  ///       DELETION        ///
  public String getDeletionDistance(String word) {
    //ArrayList to store valid deletionWords
    ArrayList<String> deletionWords = new ArrayList<String>();
    //for the length of the word
    for(Integer i = 1; i < word.length(); i++){
      String str = getClosestDeletion(word, i);
      deletionWords.add(str);
    }
    //System.out.println(deletionWords.toString());
    String toReturn = "";
    Integer min_diff = 100;
    ArrayList<String> same_word_lengths = new ArrayList<String>();
    for (String item : deletionWords){
      Integer difference = word.length() - item.length();
      if(difference < min_diff){
        toReturn = item;
        min_diff = difference;
      }
      else if(difference == min_diff){
        if(!same_word_lengths.contains(toReturn)){
          same_word_lengths.add(toReturn);
        }
        same_word_lengths.add(item);
      }
    }
    if(same_word_lengths.size() != 0){
      return max(this, same_word_lengths);
    }
    return toReturn;
  }
  public String getClosestDeletion(String word, Integer deletions){
    //stringList to store all valid deletions
    ArrayList<String> valid_deletions = new ArrayList<>();
    //for the length of the word
    //i is the starting character
    for(Integer i = 0; i < word.length(); i++){
      StringBuilder sb = new StringBuilder(word);
      //delete the char at i position
      String str = "";

      Integer j = deletions;
      //if there are more deletions to do and the deletions do
      //not extend past the length of the string, do more deletions
      while(i+j < word.length()){
        sb = new StringBuilder(word);
        str = sb.delete(i,i+j).toString();
        if(this.find(str) == this.root){
          j++;
        }
          //if the find is successful
        else {
          if(!valid_deletions.contains(str)){
            //System.out.println(str + " is being added to valid deletions");
            valid_deletions.add(str);
          }
          j++;
        }
      }
    }
      //if the find is unsuccessful, do a different deletion

    if(valid_deletions.size() == 0){
      return "";
    }
    String toReturn = "";
    Integer min_diff = 100;
    ArrayList<String> same_word_lengths = new ArrayList<String>();
    //else return the string with the maximum count
    for (String item : valid_deletions){
      Integer difference = word.length() - item.length();
      if(difference < min_diff){
        toReturn = item;
        min_diff = difference;
      }
      else if(difference == min_diff){
        if(!same_word_lengths.contains(toReturn)){
          same_word_lengths.add(toReturn);
        }
        same_word_lengths.add(item);
      }
    }
    if(same_word_lengths.size() != 0){
      return max(this, same_word_lengths);
    }
    return toReturn;
  }

  //      TRANSPOSITION     //
  public String getTranspositionDistance(String word) {

    ArrayList<String> arStr = new ArrayList<>();

      String str = getLowestTransposition(word, word.length());
      if(str != ""){
        if(!arStr.contains(str)){
          arStr.add(str);
        }
      }


    //System.out.println("arStr: " + arStr.toString());
    Integer maxCount = 0;
    String toReturn = "";

    //Sort out which word is the most common in arStr

    return max(this, arStr);
  }
  public String getLowestTransposition(String word, Integer transDistance){
    ArrayList<String> transpositions = new ArrayList<>();

    for(Integer i = 0; i < word.length(); i++){

      Integer j = 0;
      while(j <= transDistance-1){
        //System.out.println(word.charAt(i));
        //System.out.println(word.charAt(j));
        StringBuilder sb = new StringBuilder(word);
        Character temp = word.charAt(i); //d
        sb.setCharAt(i, word.charAt(j)); //g if j == 2
        sb.setCharAt(j, temp); //god
        //System.out.println(sb.toString());
        String str = sb.toString();
        if(this.find(str) == this.root){
          j++;
        }
        else {
          //System.out.println(str + " added");
          if(!transpositions.contains(str)){
            transpositions.add(str);
          }
          j++;
        }
      }

    }

    //System.out.println("StringList: " + stringList.toString());

    String toReturn = "";
    Integer maxCount = 0;
    TrieNode current = new TrieNode();
    //if theres only one item in the stringList, return it
    if(transpositions.size() == 1){
      return transpositions.get(0);
    }
    if(transpositions.size() == 0){
      return "";
    }

    for(String item : transpositions){
      current = this.find(item);
      if(current.count > maxCount)
      {
        maxCount = current.count;
        toReturn = current.getWord();
      }
    }

    return toReturn;
  }

  //      ALTERATION     //
  public String getAlterationDistance(String word){
    ArrayList<String> arStr = new ArrayList<>();

    for(Integer i = 0; i < word.length(); i++){
      String str = getLowestAlteration(word, word.length());
      if(str != null && str != ""){
        if(!arStr.contains(str)){
          arStr.add(str);
        }
      }
    }

    Integer maxCount = 0;
    String toReturn = "";

    //Sort out which word is the most common in arStr
    return max(this, arStr);
  }
  public String getLowestAlteration(String word, Integer altDistance){
    ArrayList<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
    ArrayList<String> valid_alterations = new ArrayList<String>();
    StringBuilder sb = new StringBuilder(word);
    Integer j = 0;
    while (j < altDistance){
      //reset word to original
      sb = new StringBuilder(word);
      for(Character c : alphabet){
        //change word at j position to c character in the alphabet
        sb.setCharAt(j, c);
        //check if its in the trie, then add if it is
        if(this.find(sb.toString()) == this.root){
          continue;
        }
        else {
          if( !valid_alterations.contains( sb.toString() )){
            valid_alterations.add(sb.toString());
          }
        }
      }
      //increment position
      j++;
    }
    return max(this, valid_alterations);

  }

  //      INSERTION       //
  public String getInsertionDistance(String word){
    ArrayList<String> insertions = new ArrayList<>();

    for(Integer i = 0; i < word.length(); i++){
      String str = getLowestInsertion(word, i);
      if(str != ""){
        if(!insertions.contains(str) && str != null){
          insertions.add(str);
        }
      }
    }

    Integer maxCount = 0;
    String toReturn = "";

    //Sort out which word is the most common in arStr
    for(String item : insertions){
      TrieNode n = this.find(item);
      if( n != this.root){
        if(n.count > maxCount){
          maxCount = n.count;
          toReturn = n.getWord();
        }
      }
    }
    return toReturn;
  }
  public String getLowestInsertion(String word, Integer insertionDistance){
    ArrayList<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
    ArrayList<String> valid_insertions = new ArrayList<String>();
    StringBuilder sb = new StringBuilder(word);
    int j = 0;

    while (j < insertionDistance){
      //reset word to original
      for(Character c : alphabet){
        //change word at j position to c character in the alphabet
        sb = new StringBuilder(word);
        sb = sb.insert(j, c);
        //check if its in the trie, then add if it is
        if(this.find(sb.toString()) == this.root){
          continue;
        }
        else {
          if( !valid_insertions.contains( sb.toString() ) && sb.toString() != null){
            valid_insertions.add(sb.toString());
          }
        }
      }
      //increment position
      j++;
    }
    return max(this, valid_insertions);

  }




}
