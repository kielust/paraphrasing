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
public class Match {
    private ExtToken [] ppmatch;
    private Token [] edmatch;
    private double similarity;
    private int id;
    private boolean isppused;
    private boolean isppapplied;
    private LdPPSPair ldpp;
    
    public Match(ExtToken [] ppmatch,int id, double similarity, LdPPSPair ldpp){
        this.ppmatch=ppmatch;
        this.edmatch=null;
        this.similarity=similarity;
        this.id=id;
        this.isppapplied=true;
        this.isppused= ldpp.getMatchedParaphrases().isEmpty();
        this.ldpp=ldpp;
    }
   public Match(Token [] edmatch, int id,double similarity){
        this.similarity=similarity;
        this.edmatch=edmatch;
        this.ppmatch=null;
        this.id=id;
        isppused=false;
        isppapplied=false;
        ldpp=null;
    }
   public int getId(){
       return id;
   }
   public boolean isppApplied(){
           return isppapplied;
   }
   public boolean hasPP(){
       return isppused;
   }
   public double similarity(){
       return similarity;
   } 
   public LdPPSPair getLdPP(){
       return ldpp;
   }
   public boolean hasLdPP(){
       return isppapplied;
   }
   public Token [] getEDMatch(){
       return edmatch;
   }
   
   public ExtToken [] getPPMatch(){
       return ppmatch;
   }
}
