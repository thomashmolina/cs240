package hangman;
import java.util.*;
import java.io.*;


public class HangmanGame {
  private String word;
  private ArrayList<Character> letters;
  public final int length;

  public HangmanGame(String word, Set<Character> guesses){
    this.word = word;
    this.length = word.length();
    this.letters = new ArrayList<Character>();
    for(int i = 0; i < word.length(); i++){
      this.letters.add('_');
    }
    for(int i = 0; i < word.length(); i++){
      if(guesses.contains(word.charAt(i))){
        this.letters.set(i, word.charAt(i));
      }
    }
  }

  public String toString(){
    String s = this.letters.toString();
    String toReturn = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i) != ',' && s.charAt(i) != '[' && s.charAt(i) != ']'){
        toReturn += s.charAt(i);
      }
    }
    return toReturn;
  }

  public boolean makeGuess(Character c){
    boolean guess_in_word = false;
    for(int i = 0; i < this.length; i++){
      if(this.word.charAt(i) == c){
        this.letters.set(i, c);
        guess_in_word = true;
      }
    }
    return guess_in_word;
  }

  public void fill_in_characters(List<Character> list){
    for (Character c : list){
      for(int i = 0; i < this.word.length(); i++){
        if(this.word.charAt(i) == c){
          this.letters.set(i,c);
        }
      }
    }
  }

  public boolean isDone(){
    int non_blanks = 0;
    for(Character c : letters){ if (c!='_'){ non_blanks++;} };
    return this.length == non_blanks;
  }

  public String getWord(){ return this.word; }
}
