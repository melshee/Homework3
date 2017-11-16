package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;

import core.api.IStudent; //added by mel
import core.api.impl.Student; //added by mel

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Melissa
 */
public class TestAdmin {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }
    //Definitinons for this given test suite, this allows you to define now so u don't have to keep re-defining for each test
    //Test suite:the context/environment 

    
    
    @Test
    public void testMakeClass() {//valid case: creating basic class
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void testCapacity1() { //invalid case. capacity should be > 0
    		this.admin.createClass("Capacity0", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Capacity0", 2017));
    } //assertion error: class shouldn't exist because capacity < 0
    
    @Test
    public void testCapacity2() { //invalid case. capacity should be > 0
    		this.admin.createClass("Capacity-2", 2017, "Instructor", -2);
        assertFalse(this.admin.classExists("Capacity-2", 2017));
    } //assertion error: class shouldn't exist because capacity < 0 
    
    @Test 
    public void testSameInstrDiffYears() {//valid case - same instructor has 3 classes over 3 different years
    		this.admin.createClass("Year1", 2017, "SameInstructor", 3);
    		this.admin.createClass("Year2", 2018, "SameInstructor", 4);
    		this.admin.createClass("Year3", 2019, "SameInstructor", 5);
    		assertTrue(this.admin.classExists("Year1", 2017));
    		assertTrue(this.admin.classExists("Year2", 2018));
    		assertTrue(this.admin.classExists("Year3", 2019));
    		
    		//check capacity of each class 
    		assertTrue(this.admin.getClassCapacity("Year1", 2017) == 3);
    		assertTrue(this.admin.getClassCapacity("Year2", 2018) == 4);
    		assertTrue(this.admin.getClassCapacity("Year3", 2019) == 5);
    }
    
    @Test 
    public void testSameInstrOneYear() {//invalid case - instructor has more than 2 classes in one year
    		this.admin.createClass("Class1", 2017, "SameInstructor", 4);
    		this.admin.createClass("Class2", 2017, "SameInstructor", 5);
    		this.admin.createClass("Class3", 2017, "SameInstructor", 6);
    		assertTrue(this.admin.classExists("Class1", 2017));
    		assertTrue(this.admin.classExists("Class2", 2017));
    		assertFalse(this.admin.classExists("Class3", 2017));
    } //assertion error: third class shouldn't exist
    
    @Test 
    public void testUniqueClass() {
    		this.admin.createClass("DiffClass1", 2017, "Instructor1", 5);
		this.admin.createClass("DiffClass2", 2017, "Instructor2", 9);
		
		assertTrue(this.admin.classExists("DiffClass1", 2017));
		assertTrue(this.admin.classExists("DiffClass2", 2017));
    } 
    
    @Test 
    public void testNotUniqueClass() {//invalid case - same class name nad year
    		this.admin.createClass("SameClass", 2017, "SameInstructor", 6);
    		this.admin.createClass("SameClass", 2017, "SameInstructor", 2);
		assertTrue(this.admin.classExists("SameClass", 2017));
		assertTrue(this.admin.getClassCapacity("SameClass", 2017) == 6);
		assertFalse(this.admin.getClassCapacity("SameClass", 2017) == 2);
    } 
    
    @Test 
    public void testClassDoesntExist() {
		assertFalse(this.admin.classExists("Doesn'tExist", 2017));
		assertTrue(this.admin.getClassCapacity("Doesn'tExist", 2017) == -1); //meaning class is null (doesn't exist)
    } 
    
    
    @Test 
    public void testSameClassNameAndInstrDiffYears() {
    		this.admin.createClass("SameClass", 2017, "SameInstructor", 5);
		this.admin.createClass("SameClass", 2018, "SameInstructor", 5);
		this.admin.createClass("SameClass", 2019, "SameInstructor", 5);
		
		assertTrue(this.admin.classExists("SameClass", 2017));
		assertTrue(this.admin.classExists("SameClass", 2018));
		assertTrue(this.admin.classExists("SameClass", 2019));
    } 
    
    
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    } //assertion error: incorrectly accepts a class made in 2016 (incorrect implementation)
    
    
    //input null cases 
    @Test
    public void testMakeClassNullInstr() {
        this.admin.createClass("nullClass", 2017, null, 4);
        assertFalse(this.admin.classExists("nullClass", 2017));
    } //class should not exist since instructor is null
   
    
    //tests for changeCapacity()
    
    @Test
    public void testNewValidCapacity() {
    		this.admin.createClass("4Students", 2017, "Instructor", 4);
    		
    		this.student.registerForClass("Student1", "4Students", 2017);
    		this.student.registerForClass("Student2", "4Students", 2017);
    		this.student.registerForClass("Student3", "4Students", 2017);
    		this.student.registerForClass("Student4", "4Students", 2017);
    		
    		this.admin.changeCapacity("4Students", 2017, 5);
    		assertTrue(this.admin.getClassCapacity("4Students", 2017) == 5);
    }
    
    @Test
    public void testNewSameCapacity() {
    		this.admin.createClass("1Student", 2017, "Instructor", 1);
    		
    		this.student.registerForClass("Student1", "1Student", 2017);
    		
    		this.admin.changeCapacity("3Students", 2017, 1);
    		assertTrue(this.admin.getClassCapacity("1Student", 2017) == 1);
    }
    
    @Test
    public void testInvalidCapacity() {
    		this.admin.createClass("4to3Students", 2017, "Instructor", 4);
		
		this.student.registerForClass("Student1", "4to3Students", 2017);
		this.student.registerForClass("Student2", "4to3Students", 2017);
		this.student.registerForClass("Student3", "4to3Students", 2017);
		this.student.registerForClass("Student4", "4to3Students", 2017);
		
		this.admin.changeCapacity("4to3Students", 2017, 3);
		assertTrue(this.admin.getClassCapacity("4to3Students", 2017) == 4);
    }// assertion error: capacity should not change to 3
    
}



 
