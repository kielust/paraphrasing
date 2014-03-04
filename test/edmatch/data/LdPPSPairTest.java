/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;

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
public class LdPPSPairTest {
    
    public LdPPSPairTest() {
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
     * Test of getEditDistance method, of class LdPPSPair.
     */
    @Test
    public void testGetEditDistance() {
        System.out.println("getEditDistance");
        LdPPSPair instance = null;
        short expResult = 0;
     //   short result = instance.getEditDistance();
      //  assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMatchedParaphrases method, of class LdPPSPair.
     */
    @Test
    public void testGetMatchedParaphrases() {
        System.out.println("getMatchedParaphrases");
        LdPPSPair instance = null;
        ArrayList<PPPair> expResult = null;
        ArrayList<PPPair> result = instance.getMatchedParaphrases();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSentence method, of class LdPPSPair.
     */
    @Test
    public void testGetSentence() {
        System.out.println("getSentence");
        LdPPSPair instance = null;
        Token[] expResult = null;
        Token[] result = instance.getSentence();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTokenAt method, of class LdPPSPair.
     */
    @Test
    public void testGetTokenAt() {
        System.out.println("getTokenAt");
        short index = 0;
        LdPPSPair instance = null;
        Token expResult = null;
        Token result = instance.getTokenAt(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of length method, of class LdPPSPair.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        LdPPSPair instance = null;
        short expResult = 0;
        short result = instance.length();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
