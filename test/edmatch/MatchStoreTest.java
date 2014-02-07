/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

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
public class MatchStoreTest {
    
    public MatchStoreTest() {
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
     * Test of add method, of class MatchStore.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Token[] target = null;
        MatchStore instance = null;
        instance.add(target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMatches method, of class MatchStore.
     */
    @Test
    public void testGetMatches() {
        System.out.println("getMatches");
        MatchStore instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getMatches();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasMatches method, of class MatchStore.
     */
    @Test
    public void testHasMatches() {
        System.out.println("hasMatches");
        MatchStore instance = null;
        boolean expResult = false;
        boolean result = instance.hasMatches();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasNoMatches method, of class MatchStore.
     */
    @Test
    public void testHasNoMatches() {
        System.out.println("hasNoMatches");
        MatchStore instance = null;
        boolean expResult = false;
        boolean result = instance.hasNoMatches();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
