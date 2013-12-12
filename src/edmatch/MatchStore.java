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
public class MatchStore {
    ArrayList<Token []> matches= new ArrayList();
    Token [] cand;
    MatchStore(Token [] cand){
        cand= new Token[cand.length+1];
        this.cand=cand;
    }
   public void add(Token [] target){
        matches.add(target);
    }
   public ArrayList<Token []> getMatches(){
       return matches;
   }
   public boolean hasMatches(){
       return !(matches.isEmpty());
   }
   public boolean hasNoMatches(){
       return matches.isEmpty();
   }
}
