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

import edmatch.data.Token;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rohit
 */
public class LevenshteinDistance  {

    /**
     * Get minimum of three values
     */
    private static short minimum(int a, int b, int c) {
        return (short) Math.min(a, Math.min(b, c));
    }

    /** Maximal number of items compared. */
    private static final int MAX_N = 1000;

    /**
     * Cost array, horizontally. Here to avoid excessive allocation and garbage
     * collection.
     */
    private short[] d = new short[MAX_N + 1];
    /**
     * "Previous" cost array, horizontally. Here to avoid excessive allocation
     * and garbage collection.
     */
    private short[] p = new short[MAX_N + 1];

    /*
     * Compute Levenshtein distance between two lists.
     * 
     * <p> The difference between this impl. and the canonical one is that,
     * rather than creating and retaining a matrix of size s.length()+1 by
     * t.length()+1, we maintain two single-dimensional arrays of length
     * s.length()+1.
     * 
     * <p> The first, d, is the 'current working' distance array that maintains
     * the newest distance cost counts as we iterate through the characters of
     * String s. Each time we increment the index of String t we are comparing,
     * d is copied to p, the second int[]. Doing so allows us to retain the
     * previous cost counts as required by the algorithm (taking the minimum of
     * the cost count to the left, up one, and diagonally up and to the left of
     * the current cost count being calculated). <p> (Note that the arrays
     * aren't really copied anymore, just switched... this is clearly much
     * better than cloning an array or doing a System.arraycopy() each time
     * through the outer loop.)
     * 
     * <p> Effectively, the difference between the two implementations is this
     * one does not cause an out of memory condition when calculating the LD
     * over two very large strings.
     * 
     * <p> For perfomance reasons the maximal number of compared items is {@link
     * #MAX_N}.
     */
    public int compute(Token[] s, Token[] t) {
        if (s == null || t == null)
                throw new IllegalArgumentException();
           // throw new IllegalArgumentException(OStrings.getString("LD_NULL_ARRAYS_ERROR"));

        int n = s.length; // length of s
        int m = t.length; // length of t

        if (n == 0)
            return m;
        else if (m == 0)
            return n;

        if (n > MAX_N)
            n = MAX_N;
        if (m > MAX_N)
            m = MAX_N;

        short[] swap; // placeholder to assist in swapping p and d

        // indexes into strings s and t
        short i; // iterates through s
        short j; // iterates through t

        Token t_j = null; // jth object of t

        short cost; // cost

        for (i = 0; i <= n; i++)
            p[i] = i;

        for (j = 1; j <= m; j++) {
            t_j = t[j - 1];
            d[0] = j;

            Token s_i = null; // ith object of s
            for (i = 1; i <= n; i++) {
                s_i = s[i - 1];
                cost = s_i.equals(t_j) ? (short) 0 : (short) 1;
                // minimum of cell to the left+1, to the top+1, diagonally left
                // and up +cost
                d[i] = minimum(d[i - 1] + 1, p[i] + 1, p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            swap = p;
            p = d;
            d = swap;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }
}
