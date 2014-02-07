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
public class ExtTokenTest {
    
    public ExtTokenTest() {
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
     * Test of addParaphrase method, of class ExtToken.
     */
    @Test
    public void testAddParaphrase() {
        System.out.println("addParaphrase");
        Paraphrase pp = null;
        ExtToken instance = new ExtToken();
        int expResult = 0;
        int result = instance.addParaphrase(pp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getpplist method, of class ExtToken.
     */
    @Test
    public void testGetpplist() {
        System.out.println("getpplist");
        ExtToken instance = new ExtToken();
        ArrayList<Paraphrase> expResult = null;
        ArrayList<Paraphrase> result = instance.getpplist();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getToken method, of class ExtToken.
     */
    @Test
    public void testGetToken() {
        System.out.println("getToken");
        ExtToken instance = new ExtToken();
        Token expResult = null;
        Token result = instance.getToken();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class ExtToken.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        ExtToken instance = new ExtToken();
        instance.print();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
