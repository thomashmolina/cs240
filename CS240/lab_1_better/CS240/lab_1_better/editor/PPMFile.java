package editor;
import java.util.*;
import java.io.*;
public class PPMFile {
  private String magicNum;
  private String dimensions;
  private Pixel[][] ppm_pixel_array;
  private String maxVal;

  public void setMagicNum(String num){
    this.magicNum = num;
  }
  public void setDimensions(String dims){
    this.dimensions = dims;
  }
  public void setPixelArray(Pixel[][] pix_array) {
    this.ppm_pixel_array = pix_array;
  }
  public void setMaxVal(String maxVal){
    this.maxVal = maxVal;
  }
  public String getMagicNum(){
    return this.magicNum;
  }
  public String getDimensions() {
    return this.dimensions;
  }
  public String getMaxVal(){
    return this.maxVal;
  }
  public Pixel[][] getPixelArray() {
    return this.ppm_pixel_array;
  }
}
