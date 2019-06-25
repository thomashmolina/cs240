///////////////////    IMAGE OBJECT        ///////////////////////////////////////
/* takes a  one dimensional array and the dimensions of the photo and
  creates a navegatable 2d array object.
*/
package editor;
import java.util.*;
import java.io.*;
public class Image {
  private Pixel[][] pixel_array;
  private Integer rows;
  private Integer columns;
  private Integer pixels;
  private PPMFile ppmFile;

  public Image(ArrayList<Integer> picture, Integer rows, Integer columns){
    this.rows = rows;
    this.columns = columns;
    this.pixels = 0;
    Pixel[][] pixel_array = new Pixel[rows][columns];
    ArrayList<Pixel> pixels = new ArrayList<>();
    ArrayList<Integer> list = new ArrayList<>();
    for(Integer i = 0; i < picture.size(); i+=3){
      Pixel p = new Pixel();
      for(Integer j = 0; j < 3; j++){
        if(j%3 == 0){
          p.setRedVal(picture.get(i+j));
        }
        else if(j%3 == 1){
          p.setGreenVal(picture.get(i+j));
        }
        else if(j%3 == 2){
          p.setBlueVal(picture.get(i+j));
          pixels.add(p);
        }
      }
    }

    for(Integer row = 0; row < rows; row++){
      for(Integer column = 0; column < columns; column++){
        pixel_array[row][column] = pixels.get(row * columns + column);
      }
    }
    setArray(pixel_array);
  }
  public Image(Integer rows, Integer columns) {
    this.pixel_array = new Pixel[rows][columns];
    this.rows = rows;
    this.columns = columns;
    this.pixels = rows * columns;
  }
  public int size() {
    return this.columns * this.rows;
  }
  public void addPixel(Pixel p, Integer x, Integer y) {
    this.pixel_array[x][y] = p;
  }
  public Integer getRows(){
    return this.rows;
  }
  public Integer getColumns(){
    return this.columns;
  }
  public Pixel[][] getPixelArray() {
    return this.pixel_array;
  }
  public Pixel getPixel(Integer x, Integer y) {
    try {
      //System.out.println(x + " " + y);
      return this.pixel_array[x][y];
    } catch (Exception e) {
      //System.out.println(x + " " + y);
      e.printStackTrace();
      return null;
    }
  }
  public Pixel getLeftDiagonal(Integer x, Integer y){
    try {
      return(this.pixel_array[x-1][y-1]);
    } catch(Exception e) {
      return (new Pixel(0,0,0));
    }
  }
  public Pixel getNthXposition(Integer n, Integer x, Integer y){
    try {
      return(this.pixel_array[x][y+(n-1)]);
    } catch(Exception e) {
      return null;
    }
  }
  public Integer[] calcPixelDifferences(Pixel first_pixel, Pixel second_pixel) {

    Integer redDiffs = first_pixel.getRedVal() - second_pixel.getRedVal();
    Integer greenDiffs = first_pixel.getGreenVal() - second_pixel.getGreenVal();
    Integer blueDiffs = first_pixel.getBlueVal() - second_pixel.getBlueVal();
    Integer[] diffs = {redDiffs, greenDiffs, blueDiffs};
    return diffs;

  }
  public String toString() {
    String st = "";
    for(Integer row = 0; row < this.rows; row++) {
      for(Integer column = 0; column < this.columns; column++){
        try{
          st +=" " + this.pixel_array[row][column].toString() + "\n ";
        } catch(Exception e) {
          return st;
        }
      }
      st+= '\n';
    }
    st += "\n\n There are " + this.getPixelCount()+ " pixels in this image\n\n";
    //System.out.println(st);
    return st;
  }
  public Integer getPixelCount() {
    return this.pixels;
  }
  private void setArray(Pixel[][] array) {
    this.pixel_array = array;
  }
  public void imageToFile(PPMWriter ppw, File outFile)  throws Exception{
    for(Integer i = 0; i < this.rows; i++){
      for(Integer j = 0; j < this.columns; j++){

        ppw.appendStringToFile(this.getPixel(i, j).getRedVal());
        ppw.appendStringToFile(this.getPixel(i, j).getGreenVal());
        ppw.appendStringToFile(this.getPixel(i, j).getBlueVal());

      }
    }
  }

}
