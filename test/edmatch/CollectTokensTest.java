/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.Token;
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
public class CollectTokensTest {
    
    public CollectTokensTest() {
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
     * Test of get method, of class CollectTokens.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        CollectTokens instance = new CollectTokens("888290&* He786 13th% jan-LRB- -RRB- mar-lrb- 21st 22nd january apr jun aug february march april 2nd may june july august sep october september october oct nov november dec december 1st jul 1986 is 95th feb 1670 mayhem year old 7899",true);
        CollectTokens instance2=new CollectTokens("N e786 N% MONTH MONTH N N MONTH MONTH MONTH MONTH MONTH MONTH MONTH N MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH N MONTH N is N MONTH N mayhem year old N",false);
        Token[] exptokens = instance2.get();
        Token[] rtokens = instance.get();
       // assertArrayEquals(expResult, result);
        String expResult="N e786 N% MONTH MONTH N N MONTH MONTH MONTH MONTH MONTH MONTH MONTH N MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH MONTH N MONTH N is N MONTH N mayhem year old N";
        
        String result="";
        
        for(int i=0;i<rtokens.length;i++){
            result=result+rtokens[i].getText()+" ";
        }
        result=result.trim();
        assertArrayEquals(expResult.split(" "), result.split(" "));
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test 1");
        assertArrayEquals(exptokens, rtokens);
        //     fail("The test 2");
   
    }

    /**
     * Test of main method, of class CollectTokens.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CollectTokens.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
