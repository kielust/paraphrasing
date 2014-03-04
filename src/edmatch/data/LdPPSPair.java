/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;
import java.util.ArrayList;
/**
 *
 * @author rohit
 */
public class LdPPSPair {
    private final Token[] alt;
    private final double score;
    private final ArrayList<PPPair> pppair;
    private short length;
   public LdPPSPair(Token[] alt,short length,double score,ArrayList<PPPair> pppair){
        this.alt=alt;
        this.score=score;
        this.pppair=pppair;
        this.length=length;
    }
   
   public LdPPSPair(){
       this.alt=null;
       this.score=100;
       this.pppair=new ArrayList<PPPair>();
       this.length=0;
   }
    
   public double getEditDistance(){
        return score;
    }
   public ArrayList<PPPair> getMatchedParaphrases(){
        return pppair;
    }
   public Token[] getSentence(){
        return alt;
    }
   public Token getTokenAt(short index){
        return alt[index];
    }
  public  short length(){
        return length;
    }
}
