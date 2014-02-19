/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.Token;
import edmatch.preprocess.Placeholder;
import java.util.regex.Pattern;
import java.lang.Exception;

/**
 *
 * @author rohit
 */
public class CollectTokens {
    Token[] tokens;
    int index=0;
    public CollectTokens(String sentence,boolean prepflag){
        //String sep=System.getProperty("line.separator");     
        if(prepflag)
            tokenizeWithPlaceholder(sentence);      
        else 
            tokenize(sentence);
    }
    
    private void tokenizeWithPlaceholder(String sentence){
    
            String s = Placeholder.replaceNoAndMonth(sentence);
            tokenize(s);
        
    }
    
    private void tokenize(String sentence){
        
    try{
       /* for(int i=0;i<sentence.length();i++){
            if(sentence.charAt(i)==' '){
                endindex=i;
                Token tk=new Token(sentence.substring(begindex,endindex),i);
                tokens[index++]=tk;
            }
        }
       */
                    //    System.out.print("Split this:"+sentence);
            sentence=sentence.replaceAll("\\s+"," ");
            String [] al=sentence.split(" ");
          //  System.out.print("Split successful\n");
            tokens= new Token[al.length];
            for (String al1 : al) {
                    Token tk = new Token(al1, 0);
                    tokens[index++]=tk;
                
            }
        }catch(NullPointerException e){
            System.err.println("Collect Tokens Error:"+e);
        }
    }
    
    
    public Token[]  get(){
        return tokens;
    }
    
    public static void main(String []args){
    
           CollectTokens ct=new CollectTokens("888290&* He786 13th jan mar 21st 22nd january apr jun aug february march april 2nd may june july august sep october september october oct nov november dec december 1st jul 1986 is 95th feb 1670 mayhem year old 7899",true);
           
    
    }
}
