/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

/**
 *
 * @author rohit
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import edmatch.preprocess.Placeholder;


public class CollectPP {
    HashMap<String,ArrayList<String> > ppdict=new HashMap();
   static final private  int LIMIT=50; //max allowed paraphases of a phrase 
    CollectPP(String filename){
        try{
            File file=new File(filename);
            Scanner sc=new Scanner(file);
            while(sc.hasNextLine()){
                String str=sc.nextLine();
                String[] strtokens=str.split("\\|\\|\\|");
                String left=strtokens[1].trim();
                ArrayList<String> right=new ArrayList();
                if (ppdict.containsKey(left)){
                    right=ppdict.get(left);
                     if(right.size()<LIMIT &&(!(right.contains(strtokens[2].trim())))){
                         right.add(strtokens[2].trim());
                    }
                }else right.add(strtokens[2].trim());
                ppdict.put(left,right);
            }
        }catch(FileNotFoundException e){
            System.err.print("Error opening paraphrase file"+e);
        }
    }
    
    CollectPP(String filename, boolean prepflag){
        try{
            File file=new File(filename);
            Scanner sc=new Scanner(file);
            while(sc.hasNextLine()){
                String str=sc.nextLine();
                String[] strtokens=str.split("\\|\\|\\|");
                String left=strtokens[1].trim();
                String strtokens2=strtokens[2].trim();
                if(prepflag){
                    left=Placeholder.replaceNoAndMonth(left);
                    strtokens2=Placeholder.replaceNoAndMonth(strtokens2);
                }
                ArrayList<String> right=new ArrayList();
                if (ppdict.containsKey(left)){
                    right=ppdict.get(left);
                     if(right.size()<LIMIT &&(!(right.contains(strtokens2)))){
                         right.add(strtokens2);
                    }
                }else right.add(strtokens2);
                ppdict.put(left,right);
            }
        }catch(FileNotFoundException e){
            System.err.print("Error opening paraphrase file"+e);
        }
    }
    public HashMap<String, ArrayList<String>> getPPDictionary(){
        return ppdict;
    }
    
    public static void main(String [] args){
    String ppfilename="//Users//rohit//expert//corpusparaphrase//ppdbsphrasal.txt";
        CollectPP cpp=new CollectPP(ppfilename,true);
        HashMap<String, ArrayList<String> > ppdict=cpp.getPPDictionary();
        System.out.println(Arrays.toString(ppdict.keySet().toArray()));
        
    }
    /**
     * Delete all the mappings , the dictionary will be empty after this operation
     */
    void delete(){
        if(ppdict.isEmpty()){
            System.err.print("Can't delete ppdict , already empty!!!");
        }
        else ppdict.clear();
    }
}
