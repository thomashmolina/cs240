package hangman;
import hangman.IEvilHangmanGame;
import java.util.*;
import java.io.*;

public class EvilHangmanGame implements IEvilHangmanGame{
  /**
	 * Starts a new game of evil hangman using words from <code>dictionary</code>
	 * with length <code>wordLength</code>.
	 *	<p>
	 *	This method should set up everything required to play the game,
	 *	but should not actually play the game. (ie. There should not be
	 *	a loop to prompt for input from the user.)
	 *
	 * @param dictionary Dictionary of words to use for the game
	 * @param wordLength Number of characters in the word to guess
	 */
  private Set<String> remaining_words;
  private Set<Character> remaining_letters;
  private Set<Character> guesses;
  private String current_state;
  private Word word;
  private final Integer wordLength;


  //////INITIALIZERS///////
  public EvilHangmanGame(Integer wordLength) {
    this.remaining_words = new HashSet<String>();
    this.wordLength = wordLength ;
    this.remaining_letters = new HashSet<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
    this.guesses = new HashSet<Character>();
  }
  public Word getWord(){
    return this.word;
  }
  public Set<String> getRemainingWords(){
    return this.remaining_words;

  }
  public Set<Character> getGuesses(){
    return this.guesses;
  }
  public boolean isDone() {
    for(int i = 0; i < this.word.getCharacters().size(); i++){
      if(this.word.getCharacters().get(i) == '_'){
        return false;
      }
    }
    return true;
  }


	public void startGame(File dictionary, int wordLength) {
    //fill the dictionary
    String str = "";
    try {
      BufferedReader br = new BufferedReader(new FileReader(dictionary));
      while ( (str = br.readLine()) != null ){
          if(str.length() == wordLength){
            this.remaining_words.add(str);
          }
      }
      br.close();
    } catch(Exception e){
      e.printStackTrace();
    }


    //set the wordLength
    //this.wordLength = wordLength;
    this.current_state = "";
    this.word = new Word(wordLength);
    //System.out.println("This word = " + this.word.toString() + " at the beginning");
    for(Integer i = 0; i < wordLength; i++){
      current_state += "_ ";
    }
    //remove all words that are not the desired length
    for(String s : this.remaining_words){
      if(s.length() != this.wordLength){
        this.remaining_words.remove(s);
      }
    }

  }

  //////GETTERS///////
  public String getInformation() {
    return getRemainingLetters() + "\n\n" + "Previous Guesses: " + this.guesses.toString() + "\n\n"+  getCurrentState();
  }
  public String getRemainingLetters() {
    return "Remaining Letters: " + this.remaining_letters.toString();
  }
  public void removeFromRemainingLetters(Character c){
    this.remaining_letters.remove(c);
  }
  public String getCurrentState(){
    return this.current_state;
  }

  //////FUNCTIONALITY///////
  public void narrowDownWords(Character guess){
    //find if there are any words without the current letter anywhere
    //System.out.println("Guess is " + guess);
    Set<String> to_remove = new HashSet<String>();
    for(String item : remaining_words){
      for(int i = 0; i < item.length(); i++){
        if(item.charAt(i) == guess){
          to_remove.add(item);
          break;
        }
      }
    }
    //System.out.println("removing: " + to_remove.toString());
    if(this.remaining_words.size() - to_remove.size() <= 0){
      //find the best possible set of words
      //System.out.println("Unsuccessful remove, getting best fit word set");
      Set<String> new_set = this.word.getBestFitWordSet(this.remaining_words, guess);
      this.remaining_words = new_set;
    }
    else {
      this.remaining_words.removeAll(to_remove);
      //System.out.println("Successful remove, remaining words are: ");
      //System.out.println(this.remaining_words.toString());
    }
  }

	public Set<String> makeGuess(Character guess) throws GuessAlreadyMadeException{
    return new HashSet<String>();
  }
}
