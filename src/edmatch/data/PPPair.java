/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;

import edmatch.data.Paraphrase;

/**
 *
 * @author rohit
 */
public class PPPair{
           public PPPair(Paraphrase paraphrase,int location){
            this.paraphrase=paraphrase;
            this.location=location;
            }
           public PPPair(){
           this.paraphrase=null;
           this.location=-1;
           }
         public Paraphrase getParaphrase(){
             return paraphrase;
         }
         public int getLocation(){
             return location;
         }
         private Paraphrase paraphrase;
        private int location;
        
        }