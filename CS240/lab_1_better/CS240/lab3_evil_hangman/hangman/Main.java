package hangman;
import hangman.*;
import java.lang.*;
import java.util.*;
import java.io.File;
import java.io.*;
public class Main {
  public static String winning_word = "";
  public static void print(String s){
    System.out.println(s);
  }
  public static void print(Character c){
    System.out.println(c);
  }
  public static void main(String[] args) {
    //System arguments
    String fileName = args[0];
    Integer word_length = Integer.parseInt(args[1]);
    Integer guesses = Integer.parseInt(args[2]);
    File dictionary_file = new File(fileName);

    // Game sequence
    EvilHangmanGame game = new EvilHangmanGame(word_length);
    game.startGame(dictionary_file, word_length);
    Integer turns = 0;
    BufferedReader terminal_reader = new BufferedReader(new InputStreamReader(System.in));
    while (turns < guesses && !game.isDone()) {

      //Narrow down the words till 1
      while(game.getRemainingWords().size()!=1 && turns < guesses){
        //take in the guess
        System.out.println("Guesses remaining: " + guesses + "\n\n" + game.getInformation());
        System.out.println("What's your next guess? Guess: ");
        //check if it's a character
        Character guess = '_';
        boolean valid_guess = false;
        try{
          String input = terminal_reader.readLine();
          if(input.length() == 1){
            if(Character.isLetter(input.charAt(0)) && !game.getGuesses().contains(input.charAt(0))){
              guess = input.charAt(0);
              game.removeFromRemainingLetters(guess);
              game.getGuesses().add(guess);
              valid_guess = true;
            }
          }
          else {
            System.out.println("not a valid letter");
          }
        } catch(Exception e){
          e.printStackTrace();
        }
        if(!valid_guess){
          System.out.println("Thats not a valid input");
          continue;
        }
        //narrow down the words based on the guess
        int before = game.getRemainingWords().size();
        game.narrowDownWords(guess);
        int after = game.getRemainingWords().size();
        // tell them if they got one
        if(before >= after){
          System.out.println("Sike, thats the wrong letter");
          guesses--;
          System.out.println(game.getRemainingWords().toString());
        }
        else {
          System.out.println("Oh... you got one");
        }
      }
      String str = "";
      for(String item : game.getRemainingWords()){
        str += item;
      }
      game.getWord().setStringRep(str);
      //System.out.println("Transferring word to regular game " + game.getWord().getStringRep());
      //Play regular game of hangman
      HangmanGame regularGame = new HangmanGame(game.getWord().getStringRep(), game.getGuesses());
      List<Character> correct_characters = game.getWord().getCharacters();
      regularGame.fill_in_characters(correct_characters);

      while(turns < guesses && !regularGame.isDone()){
        winning_word = regularGame.getWord();
        //play regular hangman with word
        System.out.println("Guesses remaining: " + guesses + "\n\n" + regularGame.toString());
        System.out.println("What's your next guess? Guess: ");
        //check if it's a character
        Character guess = '_';

        try{
          String input = terminal_reader.readLine();
          if(input.length() == 1){
            if(Character.isLetter(input.charAt(0)));
            guess = input.charAt(0);
          }
          else {
            System.out.println("That's not a valid letter");
            continue;
          }
        } catch(Exception e){
          e.printStackTrace();
        }

        //if they got the guess correct
        if(!regularGame.makeGuess(guess)){
          System.out.println(guess + " is not in the word");
          guesses--;
        }
        else {
          System.out.println(guess + " is in the word");
        }
      }

      if(turns == guesses){
        System.out.println("You lose!");
        break;
      }
      else {
        System.out.println("You win! The word was " + winning_word);
        break;
      }
    }
    //tell them if they won

  }
}
