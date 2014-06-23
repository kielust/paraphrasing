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
    
        ArrayList<Token []> lotks1=new ArrayList();
        ArrayList<Token []> lotks2=new ArrayList();

        public ReadFile(){
        
        }
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
    
    public void ReadEvalFile(String filename, boolean phflag){
        try{
        File file=new File(filename);
        Scanner sc=new Scanner(file);
         if(sc.hasNextLine()) sc.nextLine();
        while(sc.hasNextLine()){
            String sen=sc.nextLine();
            String tmp[]=sen.split("\t");
            
        
           // System.out.println(sentence);
          //  tmSource.add(tmp[1].trim());
            CollectTokens ct1=new CollectTokens(tmp[1].trim(),phflag);
            Token [] tokens1=ct1.get();
            lotks1.add(tokens1);
            CollectTokens ct2=new CollectTokens(tmp[2].trim(),phflag);
            Token [] tokens2=ct2.get();
            lotks2.add(tokens2);
    //      
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
    public ArrayList<Token []> get1(){
        return lotks1;
    }
    public ArrayList<Token []> get2(){
        return lotks2;
    }
    
}
