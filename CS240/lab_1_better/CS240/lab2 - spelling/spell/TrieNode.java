package spell;
import java.util.*;
import java.io.*;
public class TrieNode {
  public HashMap<Character, TrieNode> children;
  public Integer count;
  private Character character;
  private String ID;
  private String word;
  boolean isEnd;

  public TrieNode() {
    this.count = 0;
    this.children = new HashMap<Character, TrieNode>();
    this.ID = UUID.randomUUID().toString();
    this.isEnd = false;
    this.word = null;
  }
  public boolean isNull(){
    if (this.word == null){
      return true;
    }
    else {
      return false;
    }
  }
  public TrieNode(Character character) {
    this.children = new HashMap<Character, TrieNode>();
    this.count = 0;
    this.character = character;
    this.ID = UUID.randomUUID().toString();
    this.isEnd = false;
    this.word = null;
  }

  public void setEnd(){
    this.isEnd = true;
  }

  public Integer getNodeCount() {
    return this.count;
  }

  public Character getCharacter() {
    return this.character;
  }
  public Integer count(){
    return this.count;
  }

  public boolean addChild(TrieNode node){
    this.children.putIfAbsent(node.getCharacter(), node);
    return true;
  }

  public void incrementCount() {
    this.count += 1;
  }

  public String toString() {
    String mainstr = "";
    if(!this.isEnd){
      for( Character c : this.children.keySet() ){
        mainstr += this.children.get(c).toString();
      }
    }
    else {
      mainstr += "\n" + this.word;
    }
    return mainstr;
  }
  public Set<Character> getChildren(){
    return this.children.keySet();
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getWord() {
    if (this.word == null){
      return "";
    }
    return this.word;
  }

  public boolean equals(TrieNode node) {
    boolean bool = true;
    if (this == node){
      bool = true;
    }
    //has the same character
    if( this.character != node.character){
      bool = false;
    }
    //has the same ID
    if( this.ID != node.ID) {
      bool = false;
    }
    return bool;
  }
}
