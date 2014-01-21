/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;
import java.util.ArrayList;
/**
 *
 * @author rohit
 */
public class LdPPSPair {
    private final Token[] alt;
    private final short score;
    private final ArrayList<PPPair> pppair;
    private final short length;
    LdPPSPair(Token[] alt,short length,short score,ArrayList<PPPair> pppair){
        this.alt=alt;
        this.score=score;
        this.pppair=pppair;
        this.length=length;
    }
    
    short getEditDistance(){
        return score;
    }
    ArrayList<PPPair> getMatchedParaphrases(){
        return pppair;
    }
    Token[] getSentence(){
        return alt;
    }
    Token getTokenAt(short index){
        return alt[index];
    }
    short length(){
        return length;
    }
}
