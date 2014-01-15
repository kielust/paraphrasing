/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;
import java.util.ArrayList;
import java.util.HashMap;
/*collect all paraphrased sentences using sentencePP and ReadFile */
/**
 *
 * @author rohit
 */
public class CollectSentencePP {
    ArrayList<SentencePP> spplist=new ArrayList();
    CollectSentencePP(ArrayList<Token []> sentences, HashMap<String, ArrayList<String>> ppdict){
        for(Token [] sentence:sentences){
            SentencePP spp=new SentencePP(sentence,ppdict);
            spplist.add(spp);
        }
    }
    ArrayList<SentencePP> getSentencePP(){
        return spplist;
    }
}
