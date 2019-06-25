import java.io.*;
import java.util.*;
import java.util.stream.*;
public class FilterIdentifier {
  static Boolean startsWithO(String s){
    if(s.charAt(0) != 'O'){
      return false;
    }
    else {
      return true;
    }
  }
  public static void main(String[] args) {
    List<String> words = new ArrayList<>();
    words.add("Olivia");
    words.add("Oliver");
    words.add("Harry");
    words.add("Amelia");
    words.add("Isla");
    words.add("Jack");
    words.add("Mia");
    words.add("Alfie");
    words.add("Natalia");
    words.stream().filter(w -> w.length() > 5).forEach(System.out::println);
    words.stream().map(w -> w.substring(0,1)).forEach(System.out::println);
    words.stream().map(w -> w.toLowerCase()).forEach(System.out::println);
    words.stream().map(w -> w.substring(0,1)).forEach(System.out::println);
    List<String> names = new ArrayList<>();
    words.stream().forEach(w -> names.add(w));
    System.out.println(names.toString());
    List<String> namesWithO = new ArrayList<>();
    words.stream().filter(w -> startsWithO(w)).forEach(w -> namesWithO.add(w));
    System.out.println(namesWithO.toString());
    words.stream().limit(5).forEach(System.out::println);
    int[] values = {1,2,4,9,16};
    IntStream stream = IntStream.of(values);
    stream.forEach(System.out::println);
    Stream<Double> randomNums = Stream.generate(Math::random);
    randomNums.limit(10).forEach(System.out::println);
  }
}
