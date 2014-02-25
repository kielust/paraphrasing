/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.preprocess;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edmatch.CollectTokens;
import edmatch.data.Token;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import edmatch.preprocess.Placeholder;
/**
 *
 * @author rohit
 */
public class processfile {
    
   // ArrayList<Token []> lotks=new ArrayList();
   // ArrayList<String> tmSource=new ArrayList();
    public processfile(String filename,String outfile){
        try{
        File file=new File(filename);
        Scanner sc=new Scanner(file);
        File ofile=new File(outfile);
        FileWriter fw= new FileWriter(ofile);
        while(sc.hasNextLine()){
            String sentence=sc.nextLine();
            String s=Placeholder.replaceNoAndMonth(sentence);
            fw.write(s+'\n');           
        }
         sc.close();
         fw.close();
        }
        catch(FileNotFoundException e){
            System.err.println(e);
        }
        catch(IOException e){
            System.err.println(e);
        }
    }
    
    public processfile(String filename,String outfile, String pp){
        try{
        File file=new File(filename);
        Scanner sc=new Scanner(file);
        File ofile=new File(outfile);
        FileWriter fw= new FileWriter(ofile);
        while(sc.hasNextLine()){
            String sentence=sc.nextLine();
            String[] strtokens=sentence.split("\\|\\|\\|");
                String left=strtokens[1].trim();
                String strtokens2=strtokens[2].trim();
                left=Placeholder.replaceNoAndMonth(left);
                String right=Placeholder.replaceNoAndMonth(strtokens2);
                String s=strtokens[0]+"|||"+left+"|||"+right+"|||"+"others";    
             fw.write(s+'\n');           
        }
         sc.close();
         fw.close();
        }
        catch(FileNotFoundException e){
            System.err.println(e);
        }
        catch(IOException e){
            System.err.println(e);
        }
    }
    
    public static void main(String args[]){
        processfile pf= new processfile("//Users//rohit//expert//corpusparaphrase//ppdblphrasal.txt","//Users//rohit//expert//corpusparaphrase//ppdblphrasal.prepfil.txt", "pp");
      // processfile pf= new processfile("//Users//rohit//expert//programs//corpus//stest//enfr2013releaseutf8.en.fil.txt.r25000","//Users//rohit//expert//programs//corpus//stest//enfr2013releaseutf8.en.prepfil.txt.r25000");

    }
   
}
