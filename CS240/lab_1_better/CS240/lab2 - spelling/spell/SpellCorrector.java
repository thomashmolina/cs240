package spell;
import spell.Trie;
import spell.ISpellCorrector;
import java.util.*;
import java.io.*;

public class SpellCorrector implements ISpellCorrector {
  private Trie t;

  public SpellCorrector() {
    this.t = new Trie();
  }
  public void useDictionary(String dictionaryFileName) throws IOException {
    File f = new File(dictionaryFileName);
    try {
			this.t.loadTrie(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
  }
  public Trie getTrie() {
    return this.t;
  }
  public String suggestSimilarWord(String inputWord){
    if(this.t.find(inputWord) != this.t.getRoot() ) {
      return inputWord;
    }
    else {
      String str = this.t.findClosestEditDistance(inputWord);
      if (str == ""){
        return "No similar word found\n";
      }
      return str;
    }
  }

}
