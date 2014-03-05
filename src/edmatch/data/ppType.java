/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;

/**
 *
 * @author rohit
 */
public class ppType{
        String src;
        String tgt;
        int start;
        int slen;
        int tlen;
        short type;
       public ppType(String src,String tgt, int start, int slen,int tlen, short type){
            this.src=src;
            this.tgt=tgt;
            this.start=start;
            this.slen=slen;
            this.tlen=tlen;
            this.type=type;
        }
       public short getType(){
           return type;
       }
       public int getSrclen(){
           return slen;
       }
       public int getTgtlen(){
           return tlen;
       }
       public String getLeft(){
           return src;
       }
       public String getRight(){
           return tgt;
       }
       public int getIndex(){
           return start;
       }
       
    }