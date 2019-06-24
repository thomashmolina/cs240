package hangman;
import java.io.*;
import java.util.*;
public class Word {
  private final int length;
  //positions
  private List<Character> characters;
  //most recently added to character and positions
  private String string_representation;
  private Set<Integer> character_positions;

  public Word(Integer length){
    //System.out.println("default constructor");
    this.length = length;
    this.character_positions = new HashSet<Integer>(length);
    this.characters = new ArrayList<Character>(length);
    for(int i = 0; i < length; i++){
      this.characters.add('_');
    }
    this.string_representation = "";
  }

  private Word(int length, List<Character> c, Set<Integer> character_positions){
    //System.out.println("constructor with c list");
    this.length = length;
    this.characters = new ArrayList<Character>(length);
    this.characters = c;
    this.character_positions = new HashSet<Integer>(character_positions);
  }

  public void setCharacters(ArrayList<Integer> positions, ArrayList<Character> characters){
    for(int i = 0; i < this.length; i++){
      this.characters.add('_');
    }
    for(Integer i : positions){
      this.character_positions.add(i);
      this.characters.set(i, characters.get(i));
    }

  }
  public void fill_matching_indicies(String word, Character guess){

    for(int i = 0; i < word.length(); i++){
      if(word.charAt(i) == guess){
        this.setCharacter(guess, i);
      }
    }
    this.string_representation = word;
    //System.out.println(this.toString());
  }
  public void addChar(Character c, Integer position){
    for(int i = 0; i < this.length; i++){
      if(i == position){
        this.character_positions.add(i);
        this.characters.add(c);
      }
      else if(this.characters.get(i) != '_'){
        continue;
      }
      else {
        this.characters.add('_');
      }
    }

  }

  public Set<Integer> get_filled_indicies(){
    return this.character_positions;
  }
  public void setStringRep(String word){
    this.string_representation = word;
  }
  /*
    takes the word and compares it to the current state of the word
    e.g. str _ _ _ _ would be compared to _ tr _ _ _ if the guess were s and
    the position where the letter is placed is 0. This would return true.
    A word would not meet conditions if s were placed in index 1 or 2.
  */
  public boolean linesUpWith(String str) {
    boolean matches = true;
    for(int i = 0; i < this.length; i++){
      if(this.characters.get(i) != '_'){
        if(this.characters.get(i) != str.charAt(i)){
          matches = false;
          break;
        }
      }

    }
    return matches;
  }

  public Set<String> getBestFitWordSet(Set<String> words, Character guess){
    //find the best fit set
    Set<String> strings_to_return = new HashSet<String>();
    Set<String> clone = new HashSet<String>(words);
    int max = 0;
    for(String item : clone){
      Set<String> temp = new HashSet<String>();
      Word w = new Word(this.length);
      for(int i = 0; i < item.length(); i++){
        if(item.charAt(i) == guess){
          w.addChar(guess, i);
        }
      }
      //System.out.println("Trying " + w.toString());
      for(String str : clone){
        if(w.linesUpWith(str)){
          temp.add(item);
          //System.out.println("Added " + item + " to a new set " + temp.toString());
        }
      }
      if(temp.size() > max){
        max = temp.size();
        strings_to_return = temp;
      }
    }
    return strings_to_return;
  }

  public Word clone(){
    Word w = new Word(this.length, this.characters, this.character_positions);
    return w;
  }
  public void setCharacter(Character c, Integer index){
    if(index >= this.characters.size()){
      //System.out.println("That Index does not exist");
      for(Integer i = 0 ; i < this.length; i++){
        this.characters.add('_');
      }
    }
    this.characters.add(index, c);
  }

  public List<Character> getCharacters() {
    return this.characters;
  }

  public Set<Integer> getCharacterPositions() {
    return this.character_positions;
  }

  public void setWord(Word w){
    this.characters = w.getCharacters();
    this.character_positions = w.getCharacterPositions();
  }

  public boolean hasCharacter(Character c){
    for(int i = 0; i < this.string_representation.length();i++){
      if(this.string_representation.charAt(i) == c){
        return true;
      }
    }
    return false;
  }

  public void fill_in_characters(Character c){
    for(int i = 0; i < this.string_representation.length();i++){
      if(this.string_representation.charAt(i) == c){
        this.characters.set(i,c);
      }
    }
  }
  public String getStringRep(){
    return this.string_representation;
  }
  public String toString(){
    String str = "";
    str += this.characters.toString();
    str += "\n";
    str += this.character_positions.toString();
    return str;
  }

}
