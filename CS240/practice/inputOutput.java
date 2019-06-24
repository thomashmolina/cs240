import java.util.*;
public class inputOutput {
  public static void main(String[] args) {

    //Comparable Practice
    ArrayList<Name> names = new ArrayList<>();
    names.add(new Name("Mike"));
    names.add(new Name("Jonathan"));
    names.add(new Name("Henry"));
    names.add(new Name("Hank"));

    Collections.sort(names);

    for(Name n: names){
      System.out.println(n.name + ": " + n.length);
    }

    System.out.println();

    //Comparator Practice
    Rock[] rocks = {new Rock(20), new Rock(18)};

    Arrays.sort(rocks, new RockComparator());
    for(int i = 0; i < rocks.length; i++){
      System.out.println(rocks[i].weight);
    }

    System.out.println();

    //Lambda expression Practice
    Comparator<Rock> rc = (first, second) -> first.weight - second.weight;
    Arrays.sort(rocks, rc);
    for(int i = 0; i < rocks.length; i++){
      System.out.println(rocks[i].weight);
    }

    Arrays.sort(rocks, (first, second) -> second.weight-first.weight);
    for(int i = 0; i < rocks.length; i++){
      System.out.println(rocks[i].weight);
    }
  }
}

public class Name implements Comparable<Name> {
  public String name;
  public final int length;
  public Name(String s){
    this.name = s;
    this.length = s.length();
  }
  public int compareTo(Name n){
    if(this.length > n.length){
      return 1;
    }
    else if(this.length < n.length){
      return -1;
    }
    else {
      return 0;
    }
  }
}

public class Rock {
  public Integer weight;
  Rock(Integer i){
    this.weight = i;
  }
}

public class RockComparator implements Comparator<Rock> {
  public int compare(Rock first, Rock second){
    return second.weight - first.weight;
  }
}
