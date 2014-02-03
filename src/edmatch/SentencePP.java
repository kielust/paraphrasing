/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.Paraphrase;
import edmatch.data.ExtToken;
import edmatch.data.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

/**
 * This class collect all the paraphrases for a 
 * given sentence, indexing will be done 
 * per token and not per char
 * @author rohit
 */
public class SentencePP {
    private ExtToken [] extsentence;
    private static final  int LIMIT=100;  // limit on maximum paraphrases per token
    SentencePP(Token [] sentence, HashMap<String,ArrayList<String>> ppdict){
        extsentence=new ExtToken[sentence.length];
        for(int i=0;i<sentence.length;i++){
            String src="";
            ArrayList<Paraphrase> alpp=new ArrayList();                    
            for(int j=i;j<sentence.length;j++){
                src=src+" "+sentence[j].getText();
                src=src.trim();
                if(ppdict.containsKey(src)){
                    ArrayList<String> als=ppdict.get(src);
                    for(String s:als){
                        Paraphrase pp=new Paraphrase(src,s,i);
                        alpp.add(pp);
                    }
                }
            }
            ExtToken exttk=new ExtToken(sentence[i],alpp);
            extsentence[i]=exttk;
        }
    }
    
    SentencePP(Token [] sentence, HashMap<String,ArrayList<String>> ppdict,boolean flag ){
        extsentence=new ExtToken[sentence.length];
        Paraphrase [][]aap=new Paraphrase[sentence.length][LIMIT];
        for(int i=0;i<sentence.length;i++){
            String src="";
            ArrayList<Paraphrase> alpp=new ArrayList();                    
            for(int j=i;j<sentence.length;j++){
                src=src+" "+sentence[j].getText();
                src=src.trim();
                if(ppdict.containsKey(src)){
                    ArrayList<String> als=ppdict.get(src);
                    for(String s:als){
                        Paraphrase pp=new Paraphrase(src,s,i);
                        alpp.add(pp);
                    }
                }
            }
            ExtToken exttk=new ExtToken(sentence[i],alpp);
            extsentence[i]=exttk;
        }
    }
    
    /**
     * 
     * @return ext sentence
     */
    ExtToken[] get(){
         return extsentence;   
    }
    
    /**
     * 
     */
    void print(){
        for(ExtToken etk:extsentence){
            etk.print();
            System.out.print(" ");
        }
    }
 /* Test Code */   
    public static void main(String args[]){
            String ppfilename="//Users//rohit//expert//corpusparaphrase//ppdbsphrasal.txt";
        CollectPP cpp=new CollectPP(ppfilename);
        HashMap<String, ArrayList<String> > ppdict=cpp.getPPDictionary();
        System.out.println(Arrays.toString(ppdict.keySet().toArray()));
        Token []tk=new Token[5];
        Token tk1 =new Token("china-u.s.",0);
        Token tk2 =new Token("and",0);
        Token tk3 =new Token("imprudent",0);
        Token tk4 =new Token("was",0);
        Token tk5 =new Token("enthusiastic",0);
        tk[0]=tk1;tk[1]=tk2;tk[2]=tk3;tk[3]=tk4;tk[4]=tk5;
        SentencePP spp=new SentencePP(tk,ppdict);
        ExtToken []etk=spp.get();
        for(int i=0;i<5;i++){
            String tktext=etk[i].getToken().getText();
            for(int j=0;j<etk[i].getpplist().size();j++){
            String tkparaleft=etk[i].getpplist().get(j).getleft();
            String tkpararight=etk[i].getpplist().get(j).getright();
            }
        }
         
    }
}
