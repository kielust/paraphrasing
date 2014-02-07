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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author rohit
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({edmatch.data.ParaphraseTest.class, edmatch.data.TokenTest.class, edmatch.data.LdPPSPairTest.class, edmatch.data.ExtTokenTest.class, edmatch.data.PPPairTest.class})
public class DataSuite {

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
