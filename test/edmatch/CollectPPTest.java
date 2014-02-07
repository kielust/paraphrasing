/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

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
public class CollectPPTest {
    
    public CollectPPTest() {
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
     * Test of getPPDictionary method, of class CollectPP.
     */
    @Test
    public void testGetPPDictionary() {
        System.out.println("getPPDictionary");
        CollectPP instance = null;
        HashMap<String, ArrayList<String>> expResult = null;
        HashMap<String, ArrayList<String>> result = instance.getPPDictionary();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class CollectPP.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CollectPP.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class CollectPP.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        CollectPP instance = null;
        instance.delete();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
