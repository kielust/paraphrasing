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
public class Paraphrase {
    String src;
    String srcpp;
     Token [] srcpptk;
    int lensrc;
    int lensrcpp;
    int tokenno;
    //double prob;
    //other parameters
    Paraphrase(String src, String srcpp,int tokenno){
        this.src=src.trim();
        this.srcpp=srcpp.trim();
        this.tokenno=tokenno;
        lensrc=src.split(" ").length;
        lensrcpp=srcpp.split(" ").length;
        srcpptk=new Token[lensrcpp];
        String [] srcppsplit=srcpp.split(" ");
        for(int i=0;i<lensrcpp;i++){
            Token tk=new Token(srcppsplit[i],0);
            srcpptk[i]=tk;
        }
    }
    String getleft(){
        return src;
    }
    String getright(){
        return srcpp;
    }
    int noOfWordsSrc(){
        return lensrc;
    }
    int noOfWordsSrcPP(){
        return lensrcpp;
    }
    Token [] getAllpptokens(){
        return srcpptk;
    }
    Token getpptokenAtIndex(int index){
        return srcpptk[index];
    }
    boolean haspptokenAtIndex(int index){
        return (index>=0 && index<lensrcpp);        
    }
}
