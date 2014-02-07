/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

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
@Suite.SuiteClasses({edmatch.ReadTargetFileTest.class, edmatch.EDMatchTest.class, edmatch.CollectTokensTest.class, edmatch.ParaphraseTMTest.class, edmatch.matching.MatchingSuite.class, edmatch.data.DataSuite.class, edmatch.SentencePPTest.class, edmatch.lexicalpp.LexicalppSuite.class, edmatch.CollectPPTest.class, edmatch.MatchStoreTest.class, edmatch.ReadFileTest.class})
public class EdmatchSuite {

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
