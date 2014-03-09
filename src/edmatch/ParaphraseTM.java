/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;
import edmatch.data.ExtToken;
import edmatch.data.Paraphrase;
import edmatch.data.Token;
import edmatch.data.ppType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.Map.Entry;
import java.util.HashMap;
/*collect all paraphrased sentences using sentencePP and ReadFile */
/**
 *
 * @author rohit
 */
public class ParaphraseTM {
    ArrayList<SentencePP> spplist=new ArrayList();
    HashMap<String, ArrayList<Paraphrase>> usedPP=new HashMap();
    private static final  int LIMIT=2000;  // limit on maximum paraphrases per token
    
    ParaphraseTM(ArrayList<Token []> sentences, HashMap<String, ArrayList<String>> ppdict, short [] types){
        for(Token [] sentence:sentences){
            
            ExtToken [] sext=sentencePP(sentence,ppdict,types);
            SentencePP spp=new SentencePP(sext);
            spplist.add(spp);
        }
        
          for(String  ke:usedPP.keySet()){
            System.err.print("KEY:"+ke);
           for(Paraphrase pp: usedPP.get(ke)){
               System.err.print(" Type:"+pp.getType()+" S:"+pp.getleft()+" T:"+pp.getright()+" |");
           }
           System.err.println();
        }
      
    }
    ArrayList<SentencePP> getSentencePP(){
        return spplist;
    }
    
    boolean isValidType(final short [] types,final short type){
        boolean flag=false;
        for(int i=0;i<types.length;i++){
            if(type==types[i])flag=true;
        }
        return flag;
    }
    
    ppType getType(String src, String s){
        src=src.trim();
        s=s.trim();
        String [] srcl=src.split(" ");
        String [] tarl=s.split(" ");
        ppType ppt;
        int start=0;
            int back=0;
        //    int index=0;
            String left="";
            String right="";
            for(int i=0;i<srcl.length && i<tarl.length;i++){
                if(!srcl[i].equals(tarl[i]))
                {
                    start=i;
                    break;
                }
            }
            for(int i=srcl.length, j=tarl.length; i>0 && j>0;i--, j--){
                if(srcl[i-1].equals(tarl[j-1]))back++;
                else {
                    break;
                }
            }
            int slen=srcl.length;
            int tlen=tarl.length;
            
            slen=srcl.length-start-back;
            tlen=tarl.length-start-back;
            if(slen==0||tlen==0){
                if(back>0){
                    back--;    // ABC->AC or ABC->BC
                    slen++;tlen++;
                }else{
                    start--;    //ABC->AB
                  //  slen++;tlen++;
                }
            }
            for(int i=start;i<start+slen;i++){
                left=left+srcl[i]+" ";
            }
            for(int i=start;i<start+tlen;i++){
                right=right+tarl[i]+" ";
            }
            left=left.trim();
            right=right.trim();
        if(srcl.length!=tarl.length){                        
            ppt = new ppType(left,right,start,slen,tlen, (short)4 );            
            return ppt;
        }
        else {
            if(srcl.length==1){
                    ppt = new ppType(left,right,start,slen,tlen, (short)1 );
                    return ppt;
            }else {
                if(slen==1 && tlen==1){
                    ppt = new ppType(left,right,start,slen,tlen, (short)2 );
                    return ppt;
                }else{
                    ppt = new ppType(left,right,start,slen,tlen, (short)3 );
                    return ppt;
                }
            }
        }
    }
   /* 
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
                        ppType ppt=getType(src,s);
                        Paraphrase pp=new Paraphrase(src,s,i,ppt.getType());
                        alpp.add(pp);
                    }
                }
            }
            ExtToken exttk=new ExtToken(sentence[i],key);
            usedPP.put(key, alpp);
            extsentence[i]=exttk;
        }
        return extsentence;
    }*/
    
    ExtToken[] sentencePP(Token [] sentence, HashMap<String,ArrayList<String>> ppdict, final short[] types){
        ExtToken [] extsentence;
            Paraphrase [][]aap=new Paraphrase[sentence.length][LIMIT];
            int [] aapin=new int[sentence.length];
            String [] key=new String[sentence.length];
            for(int si=0;si<sentence.length;si++){
                key[si]="||N||A||";
            }
        extsentence=new ExtToken[sentence.length];
        String keystr="";
        for(int i=0;i<sentence.length;i++){
            String src="";
        //    keystr=keystr+sentence[i]+" ";
            //String key="||N||A||";
          ///  ArrayList<Paraphrase> alpp=new ArrayList();
            //Paraphrase [] alpp = new Paraphrase[LIMIT];
            for(int j=i;j<sentence.length;j++){
                if(aapin[i]<LIMIT){
                src=src+" "+sentence[j].getText();
                src=src.trim();
              //  System.err.println("Input-> Src:"+src);
                if(ppdict.containsKey(src)){
                    if(key[i].contains("NA")){
                  //      if(key[i].contains("FFFF")){
                    //        String [] kel=key[i].split("FFFF");
                    //        key[i]=kel[0]+"FFFF"+src;
                     //   }else{
                            key[i]=key[i]+"FFFF"+src;
                    //    }
                    }
                    else key[i]=src;
               //     System.err.println("KeyNow:"+key[i]+" SRCNOW:"+src+"\n----\n");
                    ArrayList<String> als=ppdict.get(src);
                    boolean updatekey=true;
                    for(String s:als){
                        ppType ppt=getType(src,s);
               //         System.err.println("SRC_getType:"+src+" para:"+s);
                        if(isValidType(types,ppt.getType()) && aapin[i+ppt.getIndex()] <LIMIT){
           //             System.err.println("Type:"+ppt.getType()+" Index:"+ppt.getIndex()+" LS:"+ppt.getSrclen()+" LT:"+ppt.getTgtlen()+" S:"+ppt.getLeft()+" T:"+ppt.getRight());
                        Paraphrase pp;
                    //    if(ppt.getType()==(short)2 ){
                            pp=new Paraphrase(ppt.getLeft(),ppt.getRight(),i,ppt.getType());
                            aap[i+ppt.getIndex()][aapin[i+ppt.getIndex()]]=pp;
                            aapin[i+ppt.getIndex()]=aapin[i+ppt.getIndex()] +1;
                            if(updatekey){
                                if(key[i+ppt.getIndex()].equals("||N||A||")){
                                    key[i+ppt.getIndex()]="NA"+src;
           //                         System.err.println("KeyChanged at :"+ppt.getIndex()+" "+key[i+ppt.getIndex()]+" SRCNOW:"+src+"\n----\n");
                                }else key[i+ppt.getIndex()]+=("NA"+src);
                              updatekey=false;
                            }
                     //   }//else{
                         //   pp=new Paraphrase(src,s,i,ppt.getType());
                        //    aap[i][ aapin[i] ]=pp;
                       //     aapin[i]=aapin[i] +1;
                           // alpp.add(pp);
                      //  }
                     }
                  }
                }
            }
             }
         }
        for(int i=0;i<sentence.length;i++){
            ExtToken exttk=new ExtToken(sentence[i],key[i]);
            System.err.print(key[i]+" ||| ");
            ArrayList<Paraphrase> alpp=new ArrayList();
           // System.err.println(aap[i].length);
            for(int k=0;k<aap[i].length && k<aapin[i];k++){
                alpp.add(aap[i][k]);
            }
            usedPP.put(key[i], alpp);
            extsentence[i]=exttk;
        }
        System.err.println();
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
