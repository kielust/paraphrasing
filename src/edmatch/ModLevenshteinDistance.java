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

public class ModLevenshteinDistance {

    /**
     * Get minimum of three values
     */
    private static short minimum(int a, int b, int c) {
        return (short) Math.min(a, Math.min(b, c));
    }
    private short[][] mat=new short[201][201];
    public int computePP(ExtToken [] s, ExtToken [] t){
        if (s == null || t == null)
                throw new IllegalArgumentException();
        int n=s.length;
        int m=t.length;
        if(n>200)n=200;
        if(m>200)m=200;
        short i=0;
        short j=0;
        for(i=0;i<n;i++)mat[0][i]=i;
        for(i=1;i<m;i++)mat[i][0]=i;
        for(i=1;i<n && i<m;i++){
            short cost=1;
            for(j=1;j<i;j++){
                cost = s[i].equals(t[j]) ? (short)0: (short)1;
                mat[i][j]=minimum(mat[i-1][j]+1, mat[i][j-1]+1, mat[i-1][j-1]+cost);
                
                cost = s[j].equals(t[i]) ? (short)0: (short)1;
                mat[j][i]=minimum(mat[j-1][i]+1, mat[j][i-1]+1, mat[j-1][i-1]+cost);
            }
            cost = s[i].equals(t[j]) ? (short)0: (short)1;
            mat[i][j]=minimum(mat[i-1][j]+1, mat[i][j-1]+1, mat[i-1][j-1]+cost);
        }
        
        for(i=1;i<n;i++){
            short cost=1;
            for(j=1;j<m;j++){
                cost = s[i].equals(t[j]) ? (short)0: (short)1;
                mat[i][j]=minimum(mat[i-1][j]+1, mat[i][j-1]+1, mat[i-1][j-1]+cost);
               if(i<m && j<n){ 
                cost = s[j].equals(t[i]) ? (short)0: (short)1;
                mat[j][i]=minimum(mat[j-1][i]+1, mat[j][i-1]+1, mat[j-1][i-1]+cost);
               }
            }
            cost = s[i].equals(t[j-1]) ? (short)0: (short)1;
            mat[i][j-1]=minimum(mat[i-1][j-1]+1, mat[i][j-2]+1, mat[i-1][j-2]+cost);
        }
        return mat[n-1][m-1];
        //return -1;
    }
   
}
