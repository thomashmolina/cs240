package editor;
import java.util.*;
import java.io.*;

public class PPMWriter {

  private File file;

  public PPMWriter(File writeFile){

    this.file = writeFile;

  }

  public void writeMagicNum(String mn) {
    try {
      this.appendStringToFile(mn);
    } catch(Exception e){
      e.printStackTrace();
    }
  }
  public void writeDimensions(String dims) {
    try{
      this.appendStringToFile(dims);
    } catch(Exception e){
      e.printStackTrace();
    }
  }
  public void writeMaxVal(String maxVal) {
    try{
      this.appendStringToFile(maxVal);
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  public void appendStringToFile(String content) throws Exception{

    BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true));
    writer.append(content + '\n');
    writer.close();

  }
  public void appendStringToFile(Integer content) throws Exception{

    BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true));
    writer.append(content.toString() + '\n');
    writer.close();

  }

  public void appendArrayListInt(ArrayList<Integer> ints) throws Exception{
    BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true));

    for(Integer i = 0; i < ints.size(); i++){
      writer.append(ints.get(i).toString() + '\n');
    }
    writer.close();

  }

}
