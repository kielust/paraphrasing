/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

/**
 *
 * @author rohit
 */
class PPPair{
        private Paraphrase paraphrase;
        private int location;
            PPPair(Paraphrase paraphrase,int location){
            this.paraphrase=paraphrase;
            this.location=location;
            }
         public Paraphrase getParaphrase(){
             return paraphrase;
         }
         public int getLocation(){
             return location;
         }
        }