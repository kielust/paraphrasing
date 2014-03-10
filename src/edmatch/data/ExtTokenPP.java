/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rohit
 */
public class ExtTokenPP {
    ArrayList<Paraphrase> alpp34;
    HashMap<String,Short> type2hmap; 
  public  ExtTokenPP(ArrayList<Paraphrase> alpp34, HashMap<String,Short> type2hmap){
     this.alpp34=alpp34;
     this.type2hmap=type2hmap;
    }
  
  public boolean isEmpty(){
      return alpp34.isEmpty() && type2hmap.isEmpty();
  }
  
  public HashMap<String, Short> getType2PP(){
      return type2hmap;
  }
  
  public ArrayList<Paraphrase> getType34PP(){
      return alpp34;
  }
  
}
