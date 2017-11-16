package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class ExampleTest {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }
    //Definitinons for this given test suite, this allows u to define now so u don't have to keep re-defining for each test
    //Test suite:the context/environment 

    
    
    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    //passes this test case

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    //does not pass this test case (assertion failure - expected false and got true)
    //incorrectly accepts a class made in 2016 (incorrect implementation)
    
//    @Test
//    public void testName() {
//    	
//    }
    
    
    
  //capacity: check corner cases ( 0, 1, -1), correct cases, and incorrect cases 
    
    }
    


 
