/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.ExtToken;
import edmatch.data.ExtTokenPP;
import edmatch.data.Token;
import edmatch.data.Paraphrase;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Comparator;
import java.lang.Exception;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.text.NumberFormat;

/**
 *
 * @author Rohit Gupta
 * @date 23 June, 2014
 */
public class EDMatch {

    /**
     * @param str
     * @param cand
     */
    
   static  HashMap<String , ExtTokenPP > usedPhrasalPP=null;
   static  HashMap<String , HashMap<String, Double> > usedLexicalPP=null;
   
    static  HashMap<String , ExtTokenPP > usedPhrasalPP1=null;
   static  HashMap<String , HashMap<String, Double> > usedLexicalPP1=null;
    
   static  HashMap<String , ExtTokenPP > usedPhrasalPP2=null;
   static  HashMap<String , HashMap<String, Double> > usedLexicalPP2=null;
   
    public List<List<String> > allphrases1=new ArrayList();
    public List<List<String> > allphrases2=new ArrayList();
   
   public static ExtTokenPP getUsedPhrasalPPbyKey(String key){
       if(key==null)return null;
        return usedPhrasalPP1.get(key);
    
    }
   
   public static HashMap<String, Double> getUsedLexicalPPbyKey(String key){
     //  if(key==null)return null;
        return usedLexicalPP1.get(key);
    
    }
   
   public static boolean isLexicalPP(String key){
       return usedLexicalPP1.containsKey(key);
   }

    
    public static <T> T[] reverse(T[] array) {
    T[] copy = array.clone();
    Collections.reverse(Arrays.asList(copy));
    return copy;
    }

    /**
     *
     * @param tks token
     * @return
     */
    
    static void printtokens(Token [] tks){
        for (Token tk : tks) {
            System.err.print(tk.getText() + " ");
        }
        System.err.println();
    }
    
    
    
       static List<List<String> > getallPhrases1(List<SentencePP> alspp){
           List<List<String> > allphrasesfile=new ArrayList();
            
            for(SentencePP spp:alspp){
                List<String > allphrases=new ArrayList();
            
                for(ExtToken exttoken:spp.get()){
                   ExtTokenPP extpp= usedPhrasalPP1.get(exttoken.getKey());
                   
                   List<Paraphrase> lpp=extpp.getType34PP();
                   for(Paraphrase pp: lpp){
                        allphrases.add(pp.getleft());
                        allphrases.add(pp.getright());
                    }
                   if(usedLexicalPP1.containsKey(exttoken.getToken().getText())) {
                        Set<String> set=usedLexicalPP1.get(exttoken.getToken().getText()).keySet();
                 //       System.out.println("setsize:"+set.size());
                        for (String s : set) {
                           allphrases.add(s);
                        }
                    }
                    for(String s:extpp.getType2PP().keySet()){
                        allphrases.add(s);
                    }
                    allphrases.add(exttoken.getToken().getText());
                }
         //       for(String s:allphrases){
          //          System.out.print(s+"\t");
          //      }
           //     System.out.println();
                allphrasesfile.add(allphrases);
            }
           return allphrasesfile;
        }
        
        static List<List<String> > getallPhrases2(List<SentencePP> alspp){
           List<List<String> > allphrasesfile=new ArrayList();
            
            for(SentencePP spp:alspp){
                List<String > allphrases=new ArrayList();
            
                for(ExtToken exttoken:spp.get()){
                   ExtTokenPP extpp= usedPhrasalPP2.get(exttoken.getKey());
                   
                   List<Paraphrase> lpp=extpp.getType34PP();
                   for(Paraphrase pp: lpp){
                        allphrases.add(pp.getleft());
                        allphrases.add(pp.getright());
                    }
                   if(usedLexicalPP2.containsKey(exttoken.getToken().getText())) {
                        Set<String> set=usedLexicalPP2.get(exttoken.getToken().getText()).keySet();
               //         System.out.println("setsize:"+set.size());
                        for (String s : set) {
                           allphrases.add(s);
                        }
                    }
                    for(String s:extpp.getType2PP().keySet()){
                        allphrases.add(s);
                    }
                    allphrases.add(exttoken.getToken().getText());
                }
          //      for(String s:allphrases){
             //       System.out.print(s+"\t");
            //    }
              //  System.out.println();
                allphrasesfile.add(allphrases);
            }
           return allphrasesfile;
        }
        
        public int semeval(short [] types, boolean placeholder,  String ppfilename,String inputfilename){
    
        long starttime=System.nanoTime();
        // read input source file
        ReadFile rf=new ReadFile();
        rf.ReadEvalFile(inputfilename, placeholder);
        ArrayList<Token []> input1=rf.get1();
        ArrayList<Token []> input2=rf.get2();
        
        System.out.println("INPUT1SIZE:"+input1.size());
        System.out.println("INPUT2SIZE:"+input2.size());
        
        long endtime1;
            
        CollectPP cpp=new CollectPP(ppfilename, placeholder);
        HashMap<String, ArrayList<String> > ppdict=cpp.getPPDictionary();        
        System.err.print("PPDICT "+ppdict.entrySet().size());
        ParaphraseTM cspp1=new ParaphraseTM(input1,ppdict, types);
        ArrayList<SentencePP> alspp1=cspp1.getSentencePP();
        
        ParaphraseTM cspp2=new ParaphraseTM(input2,ppdict, types);
        ArrayList<SentencePP> alspp2=cspp2.getSentencePP();
        
        
        usedPhrasalPP1=cspp1.getUsedPhrasalPPDictionary();
        usedLexicalPP1=cspp1.getUsedLexicalPPDictionary();
        
        usedPhrasalPP2=cspp2.getUsedPhrasalPPDictionary();
        usedLexicalPP2=cspp2.getUsedLexicalPPDictionary();
        
        
        allphrases1=getallPhrases1(alspp1);
        allphrases2=getallPhrases2(alspp2);
        
        // delete PP dictionary
        ppdict.clear();
        System.err.println("alspp finished");
        //alspp.get(200).print();
        System.err.println();
        
        endtime1=System.nanoTime();
        System.err.println("Total time in Collecting tokens and parphrases(Seconds)="+TimeUnit.SECONDS.convert(endtime1-starttime,TimeUnit.NANOSECONDS));
       
        return 0;
    }
    
}
