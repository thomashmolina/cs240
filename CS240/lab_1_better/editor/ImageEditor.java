package editor;
import java.io.*;

import java.util.*;

class ImageEditor {
  private String inputFileName;
  private String outputFileName;
  private Integer blurLength;
  private ArrayList<Integer> image;
  private File outfile;
  private File inFile;
  private Integer image_rows;
  private Integer image_columns;
  private String maxVal;

  private void set_row_col(Integer rows, Integer columns) {
    this.image_rows = rows;
    this.image_columns = columns;
  }
  public void setOutput(File filename) {
    this.outfile = filename;
  }

  public void setImage(ArrayList<Integer> image) {
    this.image = image;
  }
  public void printImage(){
    String str = "";
    for(int i = 0; i < this.image.size(); i++){
      if(i%3 != 0){
        str += this.image.get(i) + ", ";
      }
      else {
        System.out.println("[ " + str + this.image.get(i) + " ]");
        str = "";
      }
    }
  }

  public void print2dArray(Integer[][] array, Integer rows, Integer columns) {
    for(Integer i = 0; i < rows; i++){
      for(Integer j = 0; j < columns; j++) {
        System.out.print(array[i][j].toString() + " ");
      }
      System.out.println();
    }
  }

  public static Integer max(Integer first, Integer second, Integer third) {
    Integer max = 0;
    if(Math.abs(first) >= Math.abs(second)){
      max = first;
    }
    else {
      max = second;
    }
    if(Math.abs(third) > Math.abs(max)) {
      max = third;
    }
    return max;
  }

  public Integer[][] convertTo2dArray(ArrayList<Integer> image, Integer rows, Integer columns){
    Integer[][] two_d_array = new Integer[rows][columns];
    for(Integer row = 0; row < rows; row++){
      for (Integer column = 0; column < columns ; column++) {
        if(row == 0){
          two_d_array[row][column] = image.get(column);
        }
        else {
          two_d_array[row][column] = image.get((row*columns) + column);
        }
      }
    }
    //print2dArray(two_d_array, rows, columns);
    return two_d_array;
  }


  public void printErr(){
    System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
  }


  public ImageEditor(String input, String output){

    this.inputFileName = input;
    this.outputFileName = output;
    this.blurLength = 0;

  }

  public void setBlurLength(Integer blurLength){

    if (blurLength <= 0){
      Throwable thrower = new Throwable("Invalid blur length");
      System.out.println(thrower.getMessage());
      this.blurLength = 0;
    }
    else {
      this.blurLength = blurLength;
    }

  }

  public void invert(String magicNum, String dimensions, String maxVal){
    /*
    Every color value for every pixel is changed to its inverse value
    for example 0 becomes 225
    240 becomes 15
    127 becomes 128
    minimum color value is 0, maximum is 255
    */
    ArrayList<Integer> output = new ArrayList<>();
    for(Integer i = 0; i < this.image.size(); i++){
        output.add(255-this.image.get(i));
    }

    PPMWriter pw = new PPMWriter(this.outfile);
    try {
      pw.appendStringToFile(magicNum);
      pw.appendStringToFile(dimensions);
      pw.writeMaxVal(maxVal);
      pw.appendArrayListInt(output);
    } catch(Exception e){
      e.printStackTrace();
    }

  }


  ///////////////////         GRAYSCALE            ////////////////////////////



  public void grayscale(String magicNum, String dimensions, String maxVal){
    /*
      take the sum of the r, g and b values and assign them the average
    */
    ArrayList<Integer> output = new ArrayList<>();
    //System.out.print(this.image.toString());
    for(Integer i = 0; i < this.image.size(); i+=3){
        Integer sum = 0;
        Integer j = 0;
        while ( j < 3  ) {
          sum += this.image.get(i+j);
          j++;
        }
        j = 0;
        Integer average = sum / 3;
        for ( j = 0; j < 3; j++){
          output.add(average);
        }
        sum = 0;
        j = 0;
        average = 0;
    }


    PPMWriter pw = new PPMWriter(this.outfile);
    try {
      pw.appendStringToFile(magicNum);
      pw.appendStringToFile(dimensions);
      pw.writeMaxVal(maxVal);
      pw.appendArrayListInt(output);
    } catch(Exception e){
      e.printStackTrace();
    }

  }


  ///////////////////         EMBOSS            ////////////////////////////////


  public void emboss(String magicNum, String dimensions, String maxVal){
    Image image = new Image(this.image, this.image_rows, this.image_columns);
    Image out_image = new Image(this.image_rows, this.image_columns);
    //System.out.println(image.toString() + "\n\n");
    for(Integer row = 0; row < image.getRows(); row++) {
      for(Integer column = 0; column < image.getColumns(); column++){
        Integer[] diffs = new Integer[3];
        try {
          diffs = image.calcPixelDifferences(image.getPixel(row, column), image.getPixel(row-1, column-1));
        }
        catch(Exception e) {
          out_image.addPixel(new Pixel(128,128,128), column, row);
          continue;
        }

        Integer maxDiff = max(diffs[0],diffs[1],diffs[2]);
        Integer v = 128 + maxDiff;
        if ( v < 0 ) {
          v = 0;
        }
        else if( v > 255 ) {
          v = 255;
        }
        out_image.addPixel(new Pixel(v,v,v), row, column);
      }
    }
    //System.out.println(out_image.toString());
    try{
      PPMWriter ppw = new PPMWriter(this.outfile);
      ppw.writeMagicNum(magicNum);
      ppw.writeDimensions(dimensions);
      ppw.writeMaxVal(maxVal);
      out_image.imageToFile(ppw, this.outfile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*
    Calculate the differences between red, green, and blue values for the pixel and and the
    pixel to its upper left.
    redDiff = p.redValue - image[r-1,c-1].redValue
    greenDiff = p.greenValue - image[r-1,c-1].greenValue
    blueDiff = p.blueValue - image[r-1, c-1].blueValue */

    /*Find the largest difference (positive or negative). We will call this maxDifference. We
    then add 128 to maxDifference. If there are multiple equal differences with differing signs
    (e.g. -3 and 3), favor the red difference first, then green, then blue.
    v = 128 + maxDifference*/

    //If needed, we then scale v to be between 0 and 255 by doing the following:
    //If v < 0, then we set v to 0.

    //If v > 255, then we set v to 255.

    //The pixel’s red, green, and blue values are all set to v.

    //Be sure to account for the situation where r-1 or c-1 is less than 0. V should be 128 in
    //this case.

  }
  ///////////////////         MOTIONBLUR            ////////////////////////////

  public void motionblur(String magicNum, String dimensions, String maxVal, Integer blurLength){
    //System.out.println(this.blurLength.toString());
    Image image = new Image(this.image, this.image_rows, this.image_columns);
    Image out_image = new Image(this.image_rows, this.image_columns);
    ArrayList<Integer> redValues = new ArrayList<>();
    ArrayList<Integer> greenValues = new ArrayList<>();
    ArrayList<Integer> blueValues = new ArrayList<>();
    ArrayList<Pixel> pixels = new ArrayList<>();
    for(Integer i = 0 ; i < image.getRows(); i++) {
      for (Integer j = 0; j < image.getColumns(); j++) {
        Integer current = j;
        while(blueValues.size() < blurLength && j + blurLength < image.getColumns ){
          Pixel p = getPixel(i, current);
          redValues.add(p.getRedVal());
          greenValues.add(p.getGreenVal());
          blueValues.add(p.getBlueVal());
          current++;
        }
        Pixel p = new Pixel(averageArray(redValues),averageArray(greenValues),averageArray(blueValues));
        pixels.push(p);
        redValues.remove(0);
        greenValues.remove(0);
        blueValues.remove(0);
      }
      Integer row = i;
      for(Integer column = 0; column < image.getColumns; column++){
        out_image.addPixel(pixels.get(column), column, row);
      }
      pixels = new ArrayList<>();
      redValues = new ArrayList<>();
      greenValues = new ArrayList<>();
      blueValues = new ArrayList<>();
    }
    try {
      PPMWriter ppw = new PPMWriter(this.outfile);
      ppw.writeMagicNum(magicNum);
      ppw.writeDimensions(dimensions);
      ppw.writeMaxVal(maxVal);
      out_image.imageToFile(ppw, this.outfile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Integer averageArray(ArrayList<Integer> list){
    Integer items = list.size();
    Integer sum = 0;
    for(Integer i = 0; i < items; i++){
      sum += list.get(i);
    }
    return sum/items;
  }

/////////////////////////         MAIN           //////////////////////////////


  public static void main(String[] args) throws Exception{

    ImageEditor ie = new ImageEditor(args[0].toString(), args[1].toString());
    String command = args[2].toString();

    //System.out.print(ie.inputFileName + " ");
    //System.out.print(ie.outputFileName + "\n");
    File ppm = new File(ie.inputFileName);
    File output = new File(ie.outputFileName);
    ie.setOutput(output);
    PPMReader pr = new PPMReader();
    PPMFile pf = new PPMFile();
    ArrayList<Integer> values = pr.readPPM(ppm);
    String maxVal = pr.getMaxVal();
    //System.out.println(maxVal);
    Integer rows = pr.get_row_dimension();
    Integer columns = pr.get_row_dimension();
    ie.set_row_col(rows, columns);
    //sets the ppm values to the image parameter of this class
    ie.setImage(values);
    //ie.printImage();

    pf.setMagicNum(pr.getMagicNum());
    pf.setDimensions(pr.getStringDims());
    pf.setMaxVal(pr.getMaxVal());

    Integer blurAmount = 0;
    if(args.length == 4){
      blurAmount = Integer.valueOf(args[3].toString());
    }


    if(command.equals("invert")) {
      ie.invert(pr.getMagicNum(), pr.getStringDims(), pr.getMaxVal());
    }
    else if(command.equals("grayscale")) {
      ie.grayscale(pr.getMagicNum(), pr.getStringDims(), pr.getMaxVal());
    }
    else if(command.equals("emboss")) {
      ie.emboss(pr.getMagicNum(), pr.getStringDims(), pr.getMaxVal());
    }
    else if(command.equals("motionblur")) {
      if(args.length != 4) {
        ie.printErr();
      }
      else {
        ie.setBlurLength(blurAmount);
        ie.motionblur(pr.getMagicNum(), pr.getStringDims(), pr.getMaxVal(), blurAmount);
      }
    }
    else {
      System.out.print(command + " is not a valid command "+ "\n");
      ie.printErr();
    }
  }

}
