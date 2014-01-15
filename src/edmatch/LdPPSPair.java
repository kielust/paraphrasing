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
    Token [][]alt;
    short score;
    ArrayList<PPPair> pppair;
    LdPPSPair(Token[][] alt,short score,ArrayList<PPPair> pppair){
        this.alt=alt;
        this.score=score;
        this.pppair=pppair;
    }
}
