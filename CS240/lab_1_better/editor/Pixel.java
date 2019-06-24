package editor;
import java.io.*;
import java.util.*;

public class Pixel {

  private Integer rValue;
  private Integer gValue;
  private Integer bValue;
  private Integer[] values;
  private String ID;
  public Pixel() {
    this.ID = UUID.randomUUID().toString();
  }
  public Pixel(Integer rval, Integer gval, Integer bval){
    this.rValue = rval;
    this.gValue = gval;
    this.bValue = bval;
    this.ID = UUID.randomUUID().toString();
    Integer[] vals = {0, 0, 0};
    this.values = vals;
  }
  public void setRGB(Integer[] array) {
    this.rValue = array[0];
    this.gValue = array[1];
    this.bValue = array[2];
    Integer[] array_set = { this.rValue, this.gValue, this.bValue};
    this.values = array_set;
  }

  public void setRedVal(Integer value) {
    this.rValue = value;
  }
  public void setGreenVal(Integer value) {
    this.gValue = value;
  }
  public void setBlueVal(Integer value) {
    this.bValue = value;
  }
  public Integer[] getPixel() {
    Integer[] array = { this.rValue, this.gValue, this.bValue};
    return array;
  }
  public Integer[] getRGBvals() {
    Integer[] array = { this.rValue, this.gValue, this.bValue};
    return array;
  }
  public Integer getRedVal() {
    return this.rValue;
  }
  public Integer getGreenVal() {
    return this.gValue;
  }
  public Integer getBlueVal() {
    return this.bValue;
  }

  public String toString() {
    String st = "";
    st += "[" + this.rValue.toString() + ", " + this.gValue.toString() + ", " + this.bValue.toString() + "]\n";
    return st;
  }

  public boolean equals(Object otherObject) {
    return false;
  }

  public boolean equals(Pixel otherPixel) {
    if (otherPixel.ID == this.ID){
      return true;
    }
    if( otherPixel.getRGBvals() == this.getRGBvals() ){
      return true;
    }
    else {
      return false;
    }
  }

}
