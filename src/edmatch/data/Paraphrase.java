/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;

import edmatch.data.Token;

/**
 *
 * @author rohit
 */
public class Paraphrase {
    String src;
    String srcpp;
     Token [] srcpptk;
    int lensrc;
    int lensrcpp;
    int tokenno;
    short type;   //1 lexical, 2 phrasal 1diff, 3 phrasal multi
    //double prob;
    //other parameters
   public Paraphrase(String src, String srcpp,int tokenno, short type){
        this.src=src.trim();
        this.srcpp=srcpp.trim();
        this.tokenno=tokenno;
      //  System.out.println(src);
      //  System.out.println(srcpp);
        lensrc=src.split(" ").length;
        lensrcpp=srcpp.split(" ").length;
        srcpptk=new Token[lensrcpp];
        String [] srcppsplit=srcpp.split(" ");
        for(int i=0;i<lensrcpp;i++){
            Token tk=new Token(srcppsplit[i],0);
            srcpptk[i]=tk;
        }
    }
   
   public short getType(){
       return type;
   }
   public String getleft(){
        return src;
    }
   public String getright(){
        return srcpp;
    }
  public  int noOfWordsSrc(){
        return lensrc;
    }
    public int noOfWordsPP(){
        return lensrcpp;
    }
  public  Token [] getAllpptokens(){
        return srcpptk;
    }
  public  Token getpptokenAtIndex(int index){
        return srcpptk[index];
    }
  public  boolean haspptokenAtIndex(int index){
        return (index>=0 && index<lensrcpp);        
    }
  public  boolean hasSrctokenAtIndex(int index){
        return (index>=0 && index<lensrc);        
    }
  
}
