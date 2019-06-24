import java.lang.*;
import java.util.*;
public class collections {
  public static void main(String[] args) {
    List<String> friends = new LinkedList<>();
    ListIterator<String> iter = friends.listIterator();
    iter.add("Fred"); // Fred |
    iter.add("Wilma"); // Fred Wilma |
    iter.add("Barney"); // Fred | Barney  }
    System.out.println(friends.toString());
    Set<String> badWords = new HashSet<>();
    String username = "sex";
    badWords.add("sex");
    badWords.add("drugs");
    badWords.add("c++");
    if (badWords.contains(username.toLowerCase())){
      System.out.println("Please choose a different user name");
    }
  }
}
