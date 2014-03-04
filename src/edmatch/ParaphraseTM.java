/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;
import edmatch.data.ExtToken;
import edmatch.data.Paraphrase;
import edmatch.data.Token;
import java.util.ArrayList;
import java.util.HashMap;
/*collect all paraphrased sentences using sentencePP and ReadFile */
/**
 *
 * @author rohit
 */
public class ParaphraseTM {
    ArrayList<SentencePP> spplist=new ArrayList();
    HashMap<String, ArrayList<Paraphrase>> usedPP=new HashMap();
    private static final  int LIMIT=100;  // limit on maximum paraphrases per token
    
    ParaphraseTM(ArrayList<Token []> sentences, HashMap<String, ArrayList<String>> ppdict){
        for(Token [] sentence:sentences){
            
            ExtToken [] sext=sentencePP(sentence,ppdict);
            SentencePP spp=new SentencePP(sext);
            spplist.add(spp);
        }
    }
    ArrayList<SentencePP> getSentencePP(){
        return spplist;
    }
    
    short getType(String src, String s){
        String [] srcl=src.split(" ");
        String [] tarl=s.split(" ");
        if(srcl.length!=tarl.length) return (short)3;
        else {
            int slen=srcl.length;
            int tlen=srcl.length;
            for(int i=0;i<srcl.length;i++){
                if(srcl[i].equals(tarl[i]))slen--;
            }
            if(slen==1){
                if(srcl.length==1) return 1;
                else return (short)2;
            }
            return (short)0;
        }
    }
    ExtToken[] sentencePP(Token [] sentence, HashMap<String,ArrayList<String>> ppdict){
        ExtToken [] extsentence;
    
        extsentence=new ExtToken[sentence.length];
        for(int i=0;i<sentence.length;i++){
            String src="";
            String key="||N||A||";
            ArrayList<Paraphrase> alpp=new ArrayList();
            
            for(int j=i;j<sentence.length;j++){
                src=src+" "+sentence[j].getText();
                src=src.trim();
                if(ppdict.containsKey(src)){
                    key=src;
                    ArrayList<String> als=ppdict.get(src);
                    for(String s:als){
                        short type=getType(src,s);
                        Paraphrase pp=new Paraphrase(src,s,i,type);
                        alpp.add(pp);
                    }
                }
            }
            ExtToken exttk=new ExtToken(sentence[i],key);
            usedPP.put(key, alpp);
            extsentence[i]=exttk;
        }
        return extsentence;
    }
    
  /*  void sentencePP(Token [] sentence, HashMap<String,ArrayList<String>> ppdict,boolean flag ){
        ExtToken [] extsentence;
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
    }*/
      
    ArrayList<Paraphrase> getUsedPPbyKey(String key){
        return usedPP.get(key);
    
    }
    
    HashMap<String, ArrayList<Paraphrase> > getUsedPPDictionary(){
        return usedPP;
    }
    
}
