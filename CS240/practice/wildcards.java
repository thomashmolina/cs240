import java.util.*;
class wildCards {


  public static void main(String[] args) {
    Manager boss = new Manager("Rick");
    boss.setBonus(1000.00);
    boss.raiseSalary(5);
    String str = "Thomas ";
    String[] strAr = new String[25];
    String[] greetings = Arrays.repeat(10, str, strAr);
    for(String greeting : greetings){
      if(greeting != null){
        System.out.println(greeting);
      }
    }
  }
}
public class Arrays<T> {
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    public static <T> T[] repeat(int n, T obj, T[] array) {
      T[] result;
      if (array.length >= n)
          result = array;
      else {
          @SuppressWarnings("unchecked") T[] newArray = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), n);
          result = newArray;
      }
      for (int i = 0; i < n; i++) result[i] = obj;
      return result;

    }

}
public class Employee {
  private String name;
  private double salary;
  public Employee(String name) {
    this.name = name;
  }
  public void setSalary(double salary) {
    this.salary = salary;
  }
  public void raiseSalary(double raise) {
    this.salary += raise;
  }
  public double getSalary(){
    return this.salary;
  }
}

public class Manager extends Employee {
    private double bonus;
    public Manager(String name) {
      super(name);
    }
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    public double getSalary() {
      return super.getSalary() + bonus;
    }
}
