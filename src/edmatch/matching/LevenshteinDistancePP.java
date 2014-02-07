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
import edmatch.data.LdPPSPair;
import edmatch.data.PPPair;
import edmatch.data.Paraphrase;
import edmatch.data.Token;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class LevenshteinDistancePP  {

    /**
     * Get minimum of three values
     */
    private static short minimum(int a, int b, int c) {
        return (short) Math.min(a, Math.min(b, c));
    }
    
    /** Maximal number of items compared. */
    private static final int MAX_N = 1000;
    /** Maximum number of paraphrases handled for per token */
    private static final int MAX_PP = 200;
    private  final short MAX_OFFSET=50;
    /**
     * Cost array, horizontally. Here to avoid excessive allocation and garbage
     * collection.
     */
    private short[][] d = new short[MAX_PP][MAX_N + 1];
    /**
     * "Previous" cost array, horizontally. Here to avoid excessive allocation
     * and garbage collection.
     */
    private short[][] p = new short[MAX_PP][MAX_N + 1];
    
    /*private short getMaxindex(short a,short b, short c, short d){
     if(a>=b && a>=c && a>=d)return 1;
     if(b>=a && b>=c && b>=d)return 2;
     if(c>=a && c>=b && c>=d)return 3;
     if(d>=a && d>=b && d>=c)return 4;
     return 5;
    }
    private paraphrase getmaxmatchpp(extToken[] s,extToken[] t, short j, ArrayList<paraphrase> alpp){
        extToken t_j=t[j-1];
        short bestppindex=-1;
        short bestindex=-1;
        short index=0;
        short max=-1;
        short maxp=-1;
        for(paraphrase p:alpp){
           String pptext=p.srcpp;
           String [] apptext=pptext.split(" ");
           short maxtmp=0;
           short maxptmp=0;
           for(int i=0;i<p.lensrcpp;i++){
               if(s[i+j-1].equals(t[i+j-1])){
                   maxtmp++;
               }
               if(apptext[i].equals(s[i+j-1].tk.getText())){
                   maxptmp++;
               }
               short m=getMaxindex(max,maxp,maxtmp,maxptmp);
               if(m==3){
                   max=maxtmp;
                   bestindex=index;
               }else if(m==4){
                   maxp=maxptmp;
                   bestppindex=index;
               }
           }
           index++;
        }
        if(max<maxp){
            return alpp.get(bestppindex);
        }
        
        return null;
    }*/
    
    int getmaxtargetsize(ArrayList<Paraphrase> p){
        int max=0;
        for(int i=0;i<p.size();i++){
            if(p.get(i).noOfWordsSrcPP()>max)
                max=p.get(i).noOfWordsSrcPP();
        }
        return max;
    }
    
    public LdPPSPair compute(Token[] s, ExtToken[] t) {
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
        Token [][]targetsentencematch=new Token[MAX_PP][m+MAX_OFFSET];
        //ArrayList<ArrayList<Token> > targetsentencematch= new ArrayList<ArrayList<Token> >();
       //        LinkedList<LinkedList<Token>> targetsentencematch=new LinkedList<LinkedList<Token> >();

        short [][] swap; // placeholder to assist in swapping p and d
        // indexes into strings s and t
        short i; // iterates through s
        short j; // iterates through t
        ExtToken t_j = null; // jth object of t
        ExtToken t_jpext=new ExtToken();
        short cost; // cost

        for (i = 0; i <= n; i++)
            p[0][i] = i;
        int maxts=0;
        int maxtscount=1;
        boolean flag=false;
        int noiter=1;
        ArrayList<Paraphrase> alpp=new ArrayList();
        
        ArrayList<PPPair> ppusedinsentence=new ArrayList();
        short offset[]=new short[MAX_PP];    //paraphrase offset to handle diff in src and pp length
        short p_off_j=0;
       // for (j = 1; j <= m; j++) {
        for (j = 1; j <= m || flag==true; j++) {
            if(j<=m)t_j = t[j - 1];
            else t_j=new ExtToken(new Token("DUMMY",0));
            for(int k=0;k<MAX_PP;k++)
                d[k][0] = j;
            if((flag==false)){
                maxts=0; //reset 
                noiter=1; //reset
                t_jpext=null; 
            if((j<=n)&&(!t_j.getpplist().isEmpty())){
                flag=true;
                maxts=getmaxtargetsize(t_j.getpplist());
                alpp=t_j.getpplist();
                noiter=1+t_j.getpplist().size();
                t_jpext=t_j;    
                System.out.print(noiter+" "+maxts+" "+j+":");
                }
            }else if(true==flag)maxtscount++;
            
            for(int pind=0;pind<noiter;pind++){
                Token t_jp;
                if(pind>0){
                    if(t_jpext.getpplist().get(pind-1).haspptokenAtIndex(maxtscount-1)){  //pp has token
                        if(t_jpext.getpplist().get(pind-1).hasSrctokenAtIndex(maxtscount-1)){ //src && pp both have token
                    t_jp=t_jpext.getpplist().get(pind-1).getpptokenAtIndex(maxtscount-1);
                        }else{                  //pp has token but src dont have,set offset
                         offset[pind]++;
                         t_jp=t_jpext.getpplist().get(pind-1).getpptokenAtIndex(maxtscount-1);
                        }
                    }else{     //pind>0 and pp dont have token, take help from offset
                        if(t_jpext.getpplist().get(pind-1).hasSrctokenAtIndex(maxtscount-1))offset[pind]--;
                        
                        t_jp=t_j.getToken();
                    }
                }else{
                t_jp=t_j.getToken();  //
                }
                Token s_i ;//= null; // ith object of s
               targetsentencematch[pind][j+p_off_j-1]=t_jp;  //target sentence matched in calculattion of edit distance
             for (i = 1; i <= n; i++) {
                    s_i = s[i - 1];
                    cost = s_i.equals(t_jp) ? (short) 0 : (short) 1;
                    // minimum of cell to the left+1, to the top+1, diagonally left
                    // and up +cost
                    if(maxtscount==1)d[pind][i] = minimum(d[pind][i - 1] + 1, p[0][i] + 1, p[0][i - 1] + cost); //use p of basic 
                    else d[pind][i] = minimum(d[pind][i - 1] + 1, p[pind][i] + 1, p[pind][i - 1] + cost);   //use p of pp
                }
            }
            if((j<=n)&&(maxts!=0 )&& (maxts==maxtscount)){//condition changed on 15:09, 5 feb 14
                int ind=0;
                int mindistance=d[0][j+p_off_j];
                short offset_j=0;
                for(int pind=1;pind<noiter;pind++){
                    if(d[pind][j+p_off_j+offset[pind]]<d[0][j+p_off_j] && d[pind][j+p_off_j+offset[pind]]<mindistance){
                        mindistance=d[pind][j];
                        ind=pind;
                        offset_j=offset[pind];
                    }
                }
                if(ind!=0){
                    for(int k=0;k<=n;k++){
                        d[0][k]=d[ind][k];  ///d[0]=d[ind];
                    }
                    Paraphrase pp=alpp.get(ind-1);
                    PPPair pppair=new PPPair(pp,j-maxts); 
                    ppusedinsentence.add(pppair);
                    /*for(int k=j+p_off_j-maxts;k<j+p_off_j;k++){
                       // Token temp=targetsentencematch.get(ind).get(k);
                        targetsentencematch[0][k]=targetsentencematch[ind][k];
                    }
                    */
                    System.arraycopy(targetsentencematch[ind], j+p_off_j-maxts, targetsentencematch[0], j+p_off_j-maxts, j+p_off_j - (j+p_off_j-maxts));
                    j=(short) (j-offset_j);
                    p_off_j+=offset_j;
                }
                maxtscount=1;
                flag=false;
                offset=new short[MAX_PP];
                
            }
            if(j>n)flag=false;

            // copy current distance counts to 'previous row' distance counts
            swap = p;
            p = d;
            d = swap;
        }
        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        System.out.println();
        LdPPSPair ldpair;
        ldpair=new LdPPSPair(targetsentencematch[0],(short)(m+p_off_j),p[0][n],ppusedinsentence);
        return ldpair;
    }
}
