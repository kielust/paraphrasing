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
public class ParaphraseTest {
    
    public ParaphraseTest() {
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
     * Test of getleft method, of class Paraphrase.
     */
    @Test
    public void testGetleft() {
        System.out.println("getleft");
        Paraphrase instance = null;
        String expResult = "";
        String result = instance.getleft();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getright method, of class Paraphrase.
     */
    @Test
    public void testGetright() {
        System.out.println("getright");
        Paraphrase instance = null;
        String expResult = "";
        String result = instance.getright();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of noOfWordsSrc method, of class Paraphrase.
     */
    @Test
    public void testNoOfWordsSrc() {
        System.out.println("noOfWordsSrc");
        Paraphrase instance = null;
        int expResult = 0;
        int result = instance.noOfWordsSrc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of noOfWordsSrcPP method, of class Paraphrase.
     */
    @Test
    public void testNoOfWordsSrcPP() {
        System.out.println("noOfWordsSrcPP");
        Paraphrase instance = null;
        int expResult = 0;
        int result = instance.noOfWordsPP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllpptokens method, of class Paraphrase.
     */
    @Test
    public void testGetAllpptokens() {
        System.out.println("getAllpptokens");
        Paraphrase instance = null;
        Token[] expResult = null;
        Token[] result = instance.getAllpptokens();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getpptokenAtIndex method, of class Paraphrase.
     */
    @Test
    public void testGetpptokenAtIndex() {
        System.out.println("getpptokenAtIndex");
        int index = 0;
        Paraphrase instance = null;
        Token expResult = null;
        Token result = instance.getpptokenAtIndex(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of haspptokenAtIndex method, of class Paraphrase.
     */
    @Test
    public void testHaspptokenAtIndex() {
        System.out.println("haspptokenAtIndex");
        int index = 0;
        Paraphrase instance = null;
        boolean expResult = false;
        boolean result = instance.haspptokenAtIndex(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasSrctokenAtIndex method, of class Paraphrase.
     */
    @Test
    public void testHasSrctokenAtIndex() {
        System.out.println("hasSrctokenAtIndex");
        int index = 0;
        Paraphrase instance = null;
        boolean expResult = false;
        boolean result = instance.hasSrctokenAtIndex(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
