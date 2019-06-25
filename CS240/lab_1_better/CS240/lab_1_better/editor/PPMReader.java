package editor;
import java.io.*;
import java.util.*;

public class PPMReader {
  private Integer row_dimension;
  private Integer column_dimension;
  private PPMFile ppmFile;
  private String magicNum;
  private String dims;
  private String maxVal;

  public PPMReader() {
    this.magicNum = "";
    this.dims = "";
  }
  private void set_row_dimension(Integer rows) {
    this.row_dimension = rows;
  }
  private void set_column_dimension(Integer columns) {
    this.column_dimension = columns;
  }
  public Integer get_row_dimension() {
    return this.row_dimension;
  }
  public Integer get_column_dimension() {
    return this.column_dimension;
  }
  public void setMaxVal(String s){
    this.maxVal = s;
  }
  public String getMaxVal(){
    return this.maxVal;
  }
  public String getMagicNum() {
    return this.magicNum;
  }

  public boolean isInteger( String input ){

    try {
      Integer.parseInt( input );
      return true;
    } catch( Exception e ) {
        return false;
    }

  }

  public Integer[] getDimensions(String dimensions) {
    String [] strings = new String[2];
    strings = dimensions.split(" ");
    System.out.println(dimensions.toString());
    Integer [] dims = new Integer[2];
    dims[0] = (Integer.parseInt( strings[0] ));
    dims[1] = (Integer.parseInt( strings[1] ));
    return dims;
  }
  public String getStringDims(){
    return this.dims;
  }

  public boolean isDimensions(String input) {
    try {
      String [] strings = new String[2];
      strings = input.split(" ");
      for(Integer i = 0; i < strings.length; i++){
        //System.out.println("Trying " + strings[i]);

        if(!isInteger(strings[i])){
          return false;
        }
      }
      return true;
    }

    catch( Exception e ) {
        return false;
    }
  }

  public void print2dArrayList(ArrayList<Integer> array, Integer rows, Integer columns){
      Integer lines = 0;
      for(Integer i = 0; i < array.size()-1; i++){
        if(i % 100 == 0){
          System.out.println();
        }
        if(i % 1000 == 0){
          System.out.println();

        }
          System.out.print( array.get(i).toString() + " ");
          lines += 1;
      }
      // System.out.println();
      // System.out.println();
      // System.out.println("There should be " + lines.toString() + " lines in the file");

  }

  public ArrayList<Integer> readPPM(File ppmFile) throws Exception {

    PPMFile ppfile = new PPMFile();
    ArrayList<Integer> ints = new ArrayList<Integer>();
    BufferedReader br = new BufferedReader( new FileReader(ppmFile) );
    String st;
    String magicNum = br.readLine();
    this.magicNum = magicNum;
    // System.out.println(magicNum);
    ppfile.setMagicNum(magicNum);
    while( !isDimensions(st = br.readLine()) ) {}
    Integer[] dimensions = new Integer[2];
    dimensions = getDimensions(st);
    Integer rows = dimensions[0];
    this.dims += dimensions[0].toString() + " ";
    set_row_dimension(rows);
    Integer columns = dimensions[1];
    this.dims += dimensions[1].toString();
    ppfile.setDimensions(rows.toString() + " " + columns.toString());
    set_column_dimension(columns);
    String maxVal = br.readLine();
    // System.out.println("Max val " + maxVal);
    this.maxVal = maxVal;
    int i = 0;
    while( (st = br.readLine()) != null ) {
      if( isInteger( st ) ) {
        Integer point = Integer.parseInt(st);
        ints.add(point);
      }

    }
    //print2dArrayList(ints, rows, columns);
    return ints;
  }
}
