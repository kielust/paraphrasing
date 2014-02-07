/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.matching;

import edmatch.data.ExtToken;
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
public class ModLevenshteinDistanceTest {
    
    public ModLevenshteinDistanceTest() {
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
     * Test of computePP method, of class ModLevenshteinDistance.
     */
    @Test
    public void testComputePP() {
        System.out.println("computePP");
        ExtToken[] s = null;
        ExtToken[] t = null;
        ModLevenshteinDistance instance = new ModLevenshteinDistance();
        int expResult = 0;
        int result = instance.computePP(s, t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
