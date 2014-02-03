/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.Token;

/**
 *
 * @author rohit
 */
public class CollectTokens {
    Token[] tokens;
    int index=0;
    public CollectTokens(String sentence){
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
}
