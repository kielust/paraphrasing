/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
/**
 *
 * @author rohit
 */
public class ReadTargetFile {
    
    ArrayList<String> lotgtsentences=new ArrayList();
    public ReadTargetFile(String filename){
        try{
        File file=new File(filename);
        Scanner sc=new Scanner(file);
        while(sc.hasNextLine()){
            String sentence=sc.nextLine();
           // System.out.println(sentence);
            lotgtsentences.add(sentence);
    //        System.out.println(tokens[0]);
  //          for(int i=0;i<tokens.length;i++){
//                System.out.print("TEST");
      //          System.out.print(tokens[i].getLength()+" ");
        //    }
         //   System.out.println();
        }
         sc.close();
        }
        catch(IOException e){
            System.err.print(e);
        }
    }
    public ArrayList<String> get(){
        return lotgtsentences;
    }
    public int size(){
        return lotgtsentences.size();
    }
}