/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.matching;

import edmatch.data.ExtToken;
import edmatch.data.LdPPSPair;
import edmatch.data.Paraphrase;
import edmatch.data.Token;
import java.util.ArrayList;
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
public class LevenshteinDistancePPallTest {
    
    public LevenshteinDistancePPallTest() {
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
     * Test of getmaxtargetsize method, of class LevenshteinDistancePPall.
     */
    @Test
    public void testGetmaxtargetsize() {
        System.out.println("getmaxtargetsize");
        ArrayList<Paraphrase> p = null;
        LevenshteinDistancePPall instance = new LevenshteinDistancePPall();
        int expResult = 0;
        int result = instance.getmaxtargetsize(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compute method, of class LevenshteinDistancePPall.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        Token[] s = null;
        ExtToken[] t = null;
        LevenshteinDistancePPall instance = new LevenshteinDistancePPall();
        LdPPSPair expResult = null;
        LdPPSPair result = instance.compute(s, t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
