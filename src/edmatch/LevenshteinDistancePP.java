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
package edmatch;
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
            if(p.get(i).lensrcpp>max)
                max=p.get(i).lensrcpp;
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
        Token [][]targetsentencematch=new Token[MAX_PP][m];
        short[][] swap; // placeholder to assist in swapping p and d
        // indexes into strings s and t
        short i; // iterates through s
        short j; // iterates through t
        ExtToken t_j = null; // jth object of t
        ExtToken t_jpext=null;
        short cost; // cost

        for (i = 0; i <= n; i++)
            p[0][i] = i;
        int maxts=0;
        int maxtscount=1;
        boolean flag=false;
        int noiter=1;
        ArrayList<Paraphrase> alpp=new ArrayList();
        
        ArrayList<PPPair> ppusedinsentence=new ArrayList();
        for (j = 1; j <= m; j++) {
            t_j = t[j - 1];
            for(int k=0;k<MAX_PP;k++)
                d[k][0] = j;
            
            if(flag==false&&(!t_j.getpplist().isEmpty())){
                flag=true;
            maxts=getmaxtargetsize(t_j.getpplist());
            alpp=t_j.getpplist();
            noiter=1+t_j.getpplist().size();
            t_jpext=t_j;            
            }else if(true==flag)maxtscount++;
            
            for(int pind=0;pind<noiter;pind++){
                Token t_jp = null;
                if(pind>0 && t_jpext.al.get(pind-1).haspptokenAtIndex(maxtscount-1)){
                    t_jp=t_jpext.al.get(pind-1).getpptokenAtIndex(maxtscount-1);
                  targetsentencematch[pind][j-1]=t_jp;
                }else{
                t_jp=t_j.tk;
                  targetsentencematch[pind][j-1]=t_jp;
                }
                Token s_i = null; // ith object of s
                for (i = 1; i <= n; i++) {
                    s_i = s[i - 1];
                    cost = s_i.equals(t_jp) ? (short) 0 : (short) 1;
                    // minimum of cell to the left+1, to the top+1, diagonally left
                    // and up +cost
                    if(maxtscount==1)d[pind][i] = minimum(d[pind][i - 1] + 1, p[0][i] + 1, p[0][i - 1] + cost);
                    else d[pind][i] = minimum(d[pind][i - 1] + 1, p[pind][i] + 1, p[pind][i - 1] + cost);
                }
            }
            if((maxts!=0 )&& (maxts==maxtscount)){
                int ind=0;
                int mindistance=d[0][j];
                for(int mind=0;mind<noiter;mind++){
                    if(d[mind][j]<mindistance){
                        mindistance=d[mind][j];
                        ind=mind;
                    }
                }
                if(ind!=0){
                    for(int k=0;k<=n;k++){
                        d[0][k]=d[ind][k];
                    }
                    Paraphrase pp=alpp.get(ind-1);
                    PPPair pppair=new PPPair(pp,j-maxts); 
                    ppusedinsentence.add(pppair);
                    for(int k=j-maxts,l=0;k<j && l<pp.lensrcpp;k++,l++){
                        targetsentencematch[0][k]=targetsentencematch[ind][k];
                    }
                }
                maxtscount=1;
                flag=false;
            }
            // copy current distance counts to 'previous row' distance counts
            swap = p;
            p = d;
            d = swap;
        }
        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        LdPPSPair ldpair;
        ldpair=new LdPPSPair(targetsentencematch,p[0][n],ppusedinsentence);
        return ldpair;
    }
}
