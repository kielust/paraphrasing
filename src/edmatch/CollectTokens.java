/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;
import java.io.FileReader;
import java.util.ArrayList;
/**
 *
 * @author rohit
 */
public class CollectTokens {
    Token[] tokens;
    int index=0;
    public CollectTokens(String sentence){
        int begindex=0;
        int endindex=0;
        //String sep=System.getProperty("line.separator");
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

            String [] al=sentence.split(" ");
          //  System.out.print("Split successful\n");
            tokens= new Token[al.length];
            for(int i=0;i<al.length;i++){
                Token tk=new Token(al[i],0);
                tokens[index++]=tk;

            }
        }catch(NullPointerException e){
            System.err.println("Collect Tokens Error:"+e);
        }
            
    }
    public Token[]  get(){
        return tokens;
    } 
}
