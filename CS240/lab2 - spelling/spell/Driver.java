package spell;
import java.io.IOException;
import java.io.*;
import spell.Trie;
import spell.SpellCorrector;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Driver {
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {

		String dictionaryFileName = args[0];
		String inputWord = args[1];

		SpellCorrector sc = new SpellCorrector();
		sc.useDictionary(dictionaryFileName);
		System.out.println("Suggestion is: " + sc.suggestSimilarWord(inputWord));

	}

}
