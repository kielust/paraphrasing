/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;

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
public class PPPairTest {
    
    public PPPairTest() {
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
     * Test of getParaphrase method, of class PPPair.
     */
    @Test
    public void testGetParaphrase() {
        System.out.println("getParaphrase");
        PPPair instance = null;
        Paraphrase expResult = null;
        Paraphrase result = instance.getParaphrase();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocation method, of class PPPair.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        PPPair instance = null;
        int expResult = 0;
        int result = instance.getLocation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
