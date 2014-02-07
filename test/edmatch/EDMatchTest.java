/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.Token;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rohit
 */
public class EDMatchTest {
    
    public EDMatchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcSimilarity method, of class EDMatch.
     */
    @Test
    public void testCalcSimilarity() {
        System.out.println("calcSimilarity");
        Token[] str = null;
        Token[] cand = null;
        double expResult = 0.0;
        double result = EDMatch.calcSimilarity(str, cand);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcSimilarityPP method, of class EDMatch.
     */
    @Test
    public void testCalcSimilarityPP() {
        System.out.println("calcSimilarityPP");
        int str = 0;
        int cand = 0;
        int score = 0;
        double expResult = 0.0;
        double result = EDMatch.calcSimilarityPP(str, cand, score);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printtokens method, of class EDMatch.
     */
    @Test
    public void testPrinttokens() {
        System.out.println("printtokens");
        Token[] tks = null;
        EDMatch.printtokens(tks);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of extractMatch method, of class EDMatch.
     */
    @Test
    public void testExtractMatch_9args() {
        System.out.println("extractMatch");
        HashMap<String, ArrayList<String>> ppdict = null;
        double th = 0.0;
        int max = 0;
        char flag = ' ';
        ArrayList inputtokens = null;
        ArrayList tmsrctokens = null;
        ArrayList<String> tgtTM = null;
        ArrayList<String> tgtinput = null;
        String outfile = "";
        ArrayList<MatchStore> expResult = null;
        ArrayList<MatchStore> result = EDMatch.extractMatch(ppdict, th, max, flag, inputtokens, tmsrctokens, tgtTM, tgtinput, outfile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of extractMatch method, of class EDMatch.
     */
    @Test
    public void testExtractMatch_5args() {
        System.out.println("extractMatch");
        ArrayList inputtokens = null;
        ArrayList tmsrctokens = null;
        ArrayList<String> tgtTM = null;
        ArrayList<String> tgtinput = null;
        String outfile = "";
        ArrayList<MatchStore> expResult = null;
        ArrayList<MatchStore> result = EDMatch.extractMatch(inputtokens, tmsrctokens, tgtTM, tgtinput, outfile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of extractMatch method, of class EDMatch.
     */
    @Test
    public void testExtractMatch_6args() {
        System.out.println("extractMatch");
        char flag = ' ';
        ArrayList inputtokens = null;
        ArrayList<SentencePP> tmsrcexttokens = null;
        ArrayList<String> tgtTM = null;
        ArrayList<String> tgtinput = null;
        String outfile = "";
        int expResult = 0;
        int result = EDMatch.extractMatch(flag, inputtokens, tmsrcexttokens, tgtTM, tgtinput, outfile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class EDMatch.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        EDMatch.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
