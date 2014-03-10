/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.Token;
import java.util.ArrayList;
import java.util.List;
import edmatch.data.Match;
import java.util.Collections;

/**
 *
 * @author rohit
 */
public class MatchStore {
    List<Match> matches= new ArrayList();
    Token [] cand;
    MatchStore(Token [] cand){
        cand= new Token[cand.length+1];
        this.cand=cand;
    }
   public void add(Match target){
        matches.add(target);
    }
   public List<Match> getMatches(){
       return matches;
   }
   public boolean hasMatches(){
       return !(matches.isEmpty());
   }
   public boolean hasNoMatches(){
       return matches.isEmpty();
   }
   
   public List<Match> SortAndReverse(){
       Collections.sort(matches);
       Collections.reverse(matches);
       return matches;
   }
}
