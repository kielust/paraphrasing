/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.preprocess;

import java.util.regex.Pattern;

/**
 *
 * @author rohit
 */
public class Placeholder {

    /**
     * Replace -LRB-, -RRB-, -lrb-, -rrb-
     * Replace all characters except [a-z0-9%] to "" (nothing)
     * Replace all no's with 'N' and all months with 'MONTH'
     * Sample input: Charles22 date *(90)* /((% of birth is 19th jul 1917 but he died on 20 january 2006 
     * Sample output: Charles22 date N % of birth is N MONTH N but he died on N MONTH N
     * @param sentence
     * @return
     */
    
    public static String replaceNoAndMonth(String sentence){
        
            Pattern AMP=Pattern.compile("(\\-LRB\\-|\\-RRB\\-|\\-lrb\\-|\\-rrb\\-)");
            String s=AMP.matcher(sentence).replaceAll("");
          //  System.out.println(s);
                
            AMP=Pattern.compile("[^a-z0-9%\\s]");
            s=AMP.matcher(s).replaceAll("");
          //  System.out.println(s);
            AMP = Pattern.compile("\\b([0-9]*2nd|[0-9]*1st|[0-9]+th|[0-9]+)\\b");
            s=AMP.matcher(s).replaceAll("N");
          //  System.out.println(s);
            //AMP=Pattern.compile("[\\s\\^][january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|ocotober|oct|november|nov|december|dec][\\s\\$]");
            //AMP=Pattern.compile("^(?:J(anuary|u(ne|ly))|February|Ma(rch|y)|A(pril|ugust)|(((Sept|Nov|Dec)em)|Octo)ber)$");
            AMP=Pattern.compile("\\b(?:j(an(uary|)|u((ne|n)|(ly|l)))|feb(ruary|)|ma(r|rch|y)|a(pr(il|)|ug(ust|))|((sep(t|)|nov|dec)(ember|))|(oct(ober|)))\\b");
            s=AMP.matcher(s).replaceAll("MONTH");
            s=s.replaceAll("\\s+"," ");
            s=s.trim();
          //  System.out.println(s);
            return s;
    }
    /**
     * 19 july 2016
       july 19, 2016
        19th july 2016
nineteenth july 2016
nineteen july 2016
nineteen july two thousand and sixteen
nineteen july two thousand & sixteen
on nineteenth of july in the year 2016
on 19th of july in the year 2016
on 19 july in the year 2016
on 19th july in the year 2016
on nineteenth july in the year 2016
19-07-2016
07-19-2016
19/07/2016
07/19/2016
19/07/16
     * @param sentence
     * @return 
     */
    public static String replaceDate(String sentence){
        System.err.print("replaceDate NOT IMPLEMENTED YET");
        System.exit(1);
        return   "";
    }
}
