/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;
import edmatch.data.Token;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
/**
 *
 * @author rohit
 */
public class ReadFile {
    
    ArrayList<Token []> lotks=new ArrayList();
    ArrayList<String> tmSource=new ArrayList();
    public ReadFile(String filename, boolean phflag){
        try{
        File file=new File(filename);
        Scanner sc=new Scanner(file);
        while(sc.hasNextLine()){
            String sentence=sc.nextLine();
           // System.out.println(sentence);
            tmSource.add(sentence);
            CollectTokens ct=new CollectTokens(sentence,phflag);
            Token [] tokens=ct.get();
            lotks.add(tokens);
    //        System.out.println(tokens[0]);
  //          for(int i=0;i<tokens.length;i++){
//                System.out.print("TEST");
      //          System.out.print(tokens[i].getLength()+" ");
        //    }
         //   System.out.println();
        }
         sc.close();
        }
        catch(FileNotFoundException e){
            System.err.print(e);
        }
    }
    public ArrayList<Token []> get(){
        return lotks;
    }
}
