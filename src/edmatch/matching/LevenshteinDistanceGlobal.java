/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rohit
 * 
 */
package edmatch.matching;
import edmatch.data.ExtToken;
import edmatch.data.ExtTokenPP;
import edmatch.EDMatch;
import edmatch.data.LdPPSPair;
import edmatch.data.PPPair;
import edmatch.data.Paraphrase;
import edmatch.data.Token;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class LevenshteinDistanceGlobal  {

    /**
     * Get minimum of three values
     */
    private static short minimum(int a, int b, int c) {
        return (short) Math.min(a, Math.min(b, c));
    }
    
     private static double minimum(double a, double b, double c) {
        return  Math.min(a, Math.min(b, c));
    }
    
    /** Maximal number of items compared. */
    private static final int MAX_N = 1000;
    /** Maximum number of paraphrases handled for per token */
    private static final int MAX_PP = 2001;
    private  final short MAX_OFFSET=50;
    /**
     * Cost array, horizontally. Here to avoid excessive allocation and garbage
     * collection.
     */
    private double[][] d = new double[MAX_PP][MAX_N + 1];
    /**
     * "Previous" cost array, horizontally. Here to avoid excessive allocation
     * and garbage collection.
     */
    private double[][] p = new double[MAX_PP][MAX_N + 1];
    private double[] d0=new double[MAX_N +1];
    private double[] dp=new double[MAX_N +1];
    
    HashMap<Integer,double[] > dold=new HashMap();
    
    int getmaxtargetsize(ArrayList<Paraphrase> p){
        int max=0;
        for(int i=0;i<p.size();i++){
            if(p.get(i).noOfWordsPP()>max)
                max=p.get(i).noOfWordsPP();
            }
        return max;
    }
    int getmaxsourcesize(ArrayList<Paraphrase> p){
        int max=0;
        for(int i=0;i<p.size();i++){
            if(p.get(i).noOfWordsSrc()>max)
                max=p.get(i).noOfWordsSrc();
        }
        return max;
    }
    
    void printd(int pind, int upto){
        for(int i=0;i<upto;i++){
            System.err.print(d[pind][i]+"  ");
        }
        System.out.println();
    }
    
    public LdPPSPair compute(Token[] s, ExtToken[] t, double [] pppenalty ) {
        if (s == null || t == null)
                throw new IllegalArgumentException();
           // throw new IllegalArgumentException(OStrings.getString("LD_NULL_ARRAYS_ERROR"));
        int n = s.length; // length of s
        int m = t.length; // length of t
        if (n == 0)
           // return m;
            return null; //need modification
        else if (m == 0)
            return null;
            //return n;
        if (n > MAX_N)
            n = MAX_N-1;
        if (m > MAX_N)
            m = MAX_N-1;
         
       Token []targetsentencematch=new Token[m+MAX_OFFSET];
       short matchindex=0;
        //ArrayList<ArrayList<Token> > targetsentencematch= new ArrayList<ArrayList<Token> >();
       //        LinkedList<LinkedList<Token>> targetsentencematch=new LinkedList<LinkedList<Token> >();

        double [][] swap; // placeholder to assist in swapping p and d
        // indexes into strings s and t
        short i; // iterates through s
        short j; // iterates through t
        ExtToken t_j = null; // jth object of t
      //  ExtToken t_jpext=new ExtToken();
       // short cost; // cost
        double cost;
        for (i = 0; i <= n; i++)
            p[0][i] = i;
        int maxts=0;
        int maxsrc=0;
        int maxtgt=0;
        int maxtscount=1;
        boolean flag=false;
        int noiter=1;
        ArrayList<Paraphrase> alpp=new ArrayList();
        
        ArrayList<PPPair> ppusedinsentence=new ArrayList();
 //       short offset[]=new short[MAX_PP];    //paraphrase offset to handle diff in src and pp length
   //     short p_off_j=0;
       // for (j = 1; j <= m; j++) {
     //   int effj=0;
   //     short offset_j=0;
        short oldj=0;
        double oldmindistance=100;
        short effj;
        short oldeffj=1;
        for (j = 1,effj=1; effj <= m || flag==true; j++,effj++) {
       //     effj++;
      //      offset_j=0;   
            HashMap<String, Double> type1pp=new HashMap();
            HashMap<String, Short> type2pp=new HashMap();
            
            if(effj<=m){
                t_j = t[effj - 1];
                if(EDMatch.isLexicalPP(t_j.getToken().getText()))type1pp=EDMatch.getUsedLexicalPPbyKey(t_j.getToken().getText());
                type2pp=EDMatch.getUsedPhrasalPPbyKey(t_j.getKey()).getType2PP();
            }
            else t_j=new ExtToken(new Token("DUMMY",1));  //0 offset for valid token
            for(int k=0;k<MAX_PP;k++)
                d[k][0] = (short)j;
            if((flag==false)){
                maxts=0; //reset 
                noiter=1; //reset
            //    t_jpext=null; 
                maxtscount=1;
                alpp=EDMatch.getUsedPhrasalPPbyKey(t_j.getKey()).getType34PP();    
            if((j<=n)&&(!alpp.isEmpty())){
                flag=true;
                oldj=j;
                oldmindistance=p[0][oldj];
                oldeffj=effj;
                maxtgt=getmaxtargetsize(alpp);
                maxsrc=getmaxsourcesize(alpp);
                maxts=Math.max(maxtgt, maxsrc);
               // alpp=t_j.getpplist();
                noiter=1+alpp.size();
              //  t_jpext=t_j;
          //      for(int pin=1;pin<noiter;pin++){
          //          offset[pin]=(short)(alpp.get(pin-1).noOfWordsSrcPP()-alpp.get(pin-1).noOfWordsSrc());
          //      }
             //   System.out.print(noiter+" "+maxts+" "+j+":");
                }
            }else if(true==flag)maxtscount++;
            
            for(int pind=0;pind<noiter;pind++){
                Token t_jp=t_j.getToken();
                if(pind>0){
                    ////////backup d0/////
                    if(flag==true ){
                     int lensrc=alpp.get(pind-1).noOfWordsSrc();
                     if((oldj+lensrc-1)==j ||(oldj+lensrc-1)==n){
                        int valj=( (oldj+lensrc-1)>n ) ? n: (oldj+lensrc-1);
                        d0[valj]=d[0][valj];
                        dold.put(lensrc,d[0]);
                    }
                 }
                    /////////////////////////////
                    
                    if(alpp.get(pind-1).haspptokenAtIndex(maxtscount-1)){  //pp has token
                            t_jp=alpp.get(pind-1).getpptokenAtIndex(maxtscount-1);
                    }else{     //pind>0 and pp dont have token, take help from offset
                        t_jp=new Token("DUMMY",1);
                     //   if(t_j.getToken().isvalid()) {
                      //      if(  ((effj-1-offset[pind])<m)  && ( (maxtscount-1) > (-offset[pind]) )  )
                       //         t_jp=t[effj-1-offset[pind]].getToken();
                       //     else t_jp=new Token("DUMMY",1);
                      //  }
                    }
                }else{
                    
                    if(flag==true && maxtscount>maxsrc)t_jp=new Token("DUMMY",1);
                }
                Token s_i ;//= null; // ith object of s
             if(t_jp.isvalid()){   //when j>m
             //    System.out.println("   j="+j+" pind="+pind);
            //     short typep=0;
           //      if(flag==true && pind>0){typep=alpp.get(pind-1).getType();}
                 for (i = 1; i <= n; i++) {
                    s_i = s[i - 1];
                    cost = s_i.equals(t_jp) ? (short) 0 : (short) 1;
                  if(pind==0){  
                    boolean istype2ppexist=false;                    
                    if(cost==(short)1  && !type2pp.isEmpty())istype2ppexist=type2pp.containsKey(s_i.getText());
                    if(istype2ppexist)cost=(short)0;
                    boolean istype1ppexist=false;
                    if(cost==(short)1 && !type1pp.isEmpty())istype1ppexist=type1pp.containsKey(s_i.getText());
                    if(istype1ppexist)cost=(short)0;
                    ///storing pp used ///
                    if(istype1ppexist){
                        ppusedinsentence.add(new PPPair(new Paraphrase(t_jp.getText(),s_i.getText(),0,(short)1),j));
                    }else if(istype2ppexist){
                        ppusedinsentence.add(new PPPair(new Paraphrase(t_jp.getText(),s_i.getText(),0,(short)2),j));
                    }
                  //  System.out.println("type2:"+istype2ppexist+" type1:"+istype1ppexist+" cost:"+cost+" si:"+s_i.getText()+" tjp:"+t_jp.getText());
                    }
                    //  if(typep!=0 && cost==0){cost=cost+pppenalty[typep];}
                    // minimum of cell to the left+1, to the top+1, diagonally left
                    // and up +cost
                    if(maxtscount==1)d[pind][i] = minimum(d[pind][i - 1] + 1, p[0][i] + 1, p[0][i - 1] + cost); //use p of basic 
                    else d[pind][i] = minimum(d[pind][i - 1] + 1, p[pind][i] + 1, p[pind][i - 1] + cost);   //use p of pp
                //    System.out.print(s_i.getText()+" "+t_jp.getText()+" "+d[pind][i]+"\t");
                }
              //  System.out.println();
                 
                 
                if(flag==false){targetsentencematch[matchindex++]=t_jp;} //target sentence matched in calculation of edit distance
                if(maxtscount==1 && pind==0 && flag==true)dp=d[0];
             }else{
                 d[pind]=p[pind]; //copy previous value
                 if(maxtscount==1 && pind==0 && flag==true)dp=d[0];
             }
          //   System.err.println("\n"+t_jp.getText()+" j="+j+" pind:"+pind);
          //   printd(pind, 30);
            }
            if((maxts!=0 )&& (maxts==maxtscount)){//condition changed on 15:09, 5 feb 14, changed on 7 feb 18:13 
                int ind=0;
                double mindistance=500;
                //mindistance= (j>n) ? d[0][n] : mindistance;
                int validj;//= (effj>n)? n :effj;
                //mindistance= (j<=m && j<=n) ? d[0][j] : d[0][m];  //why m
               // mindistance=d[0][validj];
                int len=0;
                boolean ppwin=false;
                for(int pind=1; pind<noiter;pind++){                    
                    int lenpp=alpp.get(pind-1).noOfWordsPP();
                    validj=( (oldj+lenpp-1)>n ) ? n: (oldj+lenpp-1); 
                    if(d[pind][validj]<mindistance || (d[pind][validj]==mindistance && lenpp>=len)){
                        mindistance=d[pind][validj];
                        ind=pind;
                        len=lenpp;
                        ppwin=true;
                 //       offset_j=offset[pind];
                    }
                    int lensrc=alpp.get(pind-1).noOfWordsSrc();
                    validj=( (oldj+lensrc-1)>n ) ? n: (oldj+lensrc-1); 
                    if(d0[validj]<=mindistance || (d0[validj]==mindistance && lensrc>=len) ){
                        mindistance=d0[validj];
                        ind=pind;
                        ppwin=false;
                        len=lensrc;
                //        offset_j=0;
                    }
                }
                if(ppwin){
                        d[0]=d[ind];
                        int lensrc=alpp.get(ind-1).noOfWordsSrc();
                        int lenpp=alpp.get(ind-1).noOfWordsPP();
                 //       System.err.println("Lensrc:"+lensrc+"  \t Lenpp:"+lenpp);
                  //      System.err.println("ppwin because: ed is "+d0[( (oldj+lensrc-1)>n ) ? n: (oldj+lensrc-1)]);
                  //     System.err.println("and pp is "+d[ind][( (oldj+lenpp-1)>n ) ? n: (oldj+lenpp-1)]);
                       
                    ppusedinsentence.add(new PPPair(alpp.get(ind-1),j-maxts));
              //      for(int k=j+p_off_j-maxts;k<j+p_off_j && k<n ;k++){
                       // Token temp=targetsentencematch.get(ind).get(k);
             //           targetsentencematch[0][k]=targetsentencematch[ind][k];
             //      }
                    for(int k=0;k<alpp.get(ind-1).noOfWordsPP();k++){
                        targetsentencematch[matchindex++]=alpp.get(ind-1).getpptokenAtIndex(k);                                               
                    }
                    //System.arraycopy(targetsentencematch[ind], j+p_off_j-maxts, targetsentencematch[0], j+p_off_j-maxts, j+p_off_j - (j+p_off_j-maxts));
                 //   if(offset_j>0)j=(short)(j-offset_j);
                //    p_off_j+=offset_j;
                    effj=(short)(oldeffj-1+alpp.get(ind-1).noOfWordsSrc());
                    j=(short)(oldj-1+alpp.get(ind-1).noOfWordsPP());

                   // if(offset_j<0)effj=effj+offset_j;
                }else if(oldmindistance==mindistance && len==maxsrc){
                /// get ind for which it was same and maximum length no change in d needed 
                    for(int k=0;k<maxsrc;k++){
                            targetsentencematch[matchindex++]=t[oldeffj-1+k].getToken();                        
                        }
                    j=(short)(oldj-1+maxsrc);
                    effj=(short)(oldeffj-1+maxsrc);
                                
                }else if(oldmindistance==mindistance){
                /// get ind for which it was same 
                    d[0]=dold.get(len);
                    for(int k=0;k<len;k++){
                            targetsentencematch[matchindex++]=t[oldeffj-1+k].getToken();                        
                        }
                    j=(short)(oldj-1+len);
                    effj=(short)(oldeffj-1+len);
                                
                }else{
                    d[0]=dp;
                    for(int k=0;k<1;k++){
                        targetsentencematch[matchindex++]=t[oldeffj-1+k].getToken();                        
                    }
                j=(short)(oldj-1+1);
                effj=(short)(oldeffj-1+1);                
                    
                }
                maxtscount=1;
                flag=false;
          //      offset=new short[MAX_PP];
                
            }
            //if(j>n)flag=false;

            // copy current distance counts to 'previous row' distance counts
            swap = p;
            p = d;
            //short [][]swap2;
            d = new double[MAX_PP][MAX_N+1];   //boundry condition when j >= m, saving last value of d
            for(int dum=0;dum<MAX_PP;dum++){
                for(int dum2=0;dum2<MAX_N+1;dum2++){
                    d[dum][dum2]=500;
                }
            }
        }
        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        System.out.println();
        LdPPSPair ldpair;
        ldpair=new LdPPSPair(targetsentencematch,(short)(matchindex),p[0][n],ppusedinsentence);
       // ldpair=new LdPPSPair(targetsentencematch[0],(short)targetsentencematch[0].length,p[0][n],ppusedinsentence);
        return ldpair;
    }
}
