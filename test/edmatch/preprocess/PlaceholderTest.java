/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.preprocess;

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
public class PlaceholderTest {
    
    public PlaceholderTest() {
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
     * Test of replaceNoAndMonth method, of class Placeholder.
     */
    @Test
    public void testReplaceNoAndMonth() {
        System.out.println("replaceNoAndMonth");
        String sentence = "888290&* He786 & 13th% jan -LRB- -RRB- mar -lrb- 21st 22nd january apr jun aug february march april 2nd may june july august sep october september october oct nov november dec december 1st jul 1986 is 95th feb 1670 mayhem year old 7899";
        String expResult = "N e786 N% MONTH MONTH N N MONTH MONTH MONTH MONTH MONTH MONTH MONTH N MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH N MONTH N is N MONTH N mayhem year old N";
        String result = Placeholder.replaceNoAndMonth(sentence);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
