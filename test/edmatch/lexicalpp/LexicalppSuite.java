/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.lexicalpp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author rohit
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({edmatch.lexicalpp.TokenTest.class, edmatch.lexicalpp.LevenshteinDistanceTest.class, edmatch.lexicalpp.CollectTMTokensTest.class, edmatch.lexicalpp.vcbTest.class, edmatch.lexicalpp.ReadFileTest.class})
public class LexicalppSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
