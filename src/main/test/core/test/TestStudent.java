package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;

import core.api.IStudent; //added by mel
import core.api.impl.Student; //added by mel

import core.api.IInstructor; //added by mel
import core.api.impl.Instructor; //added by mel

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Melissa
 */
public class TestStudent {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
    //Definitinons for this given test suite, this allows you to define now so u don't have to keep re-defining for each test
    //Test suite:the context/environment 

    //testing registerForClass()
    @Test
    public void testRegisterValid() { //valid scenario, 2 students register for class of size 2, then inc capacity to 3 and add a student
    		this.admin.createClass("ClassA", 2017, "InstructorA", 2);
    		this.student.registerForClass("Student1", "ClassA", 2017);
    		this.student.registerForClass("Student2", "ClassA", 2017);
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassA", 2017));
    		assertTrue(this.student.isRegisteredFor("Student2", "ClassA", 2017));
    		
    		this.admin.changeCapacity("ClassA", 2017, 3);
    		this.student.registerForClass("Student3", "ClassA", 2017);
    		assertTrue(this.student.isRegisteredFor("Student3", "ClassA", 2017));
    }
    
    @Test
    public void testRegisterInvalidExceedCapacity() { //case: student tries to register for class with enrollment already at capacity
    		this.admin.createClass("ClassB", 2017, "InstructorB", 2);
    		this.student.registerForClass("Student1", "ClassB", 2017);
    		this.student.registerForClass("Student2", "ClassB", 2017);
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassB", 2017));
    		assertTrue(this.student.isRegisteredFor("Student2", "ClassB", 2017));
    		
    		this.student.registerForClass("Student3", "ClassB", 2017);
    		assertFalse(this.student.isRegisteredFor("Student3", "ClassB", 2017));
    }//Assertion Error: Student3 should not be registered for ClassB
    
  //testing dropClass() - provided the student is registered and the class has not ended
    
    @Test
    public void testDropClassValid() { //valid case - Student1 registers and then drops, then Student2 registers for the same class
    		this.admin.createClass("ClassC", 2017, "InstructorC", 1);
    		this.student.registerForClass("Student1", "ClassC", 2017);
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassC", 2017));
    		this.student.dropClass("Student1", "ClassC", 2017);
    		assertFalse(this.student.isRegisteredFor("Student1", "ClassC", 2017));
    		
    		this.student.registerForClass("Student2", "ClassC", 2017);
    		assertTrue(this.student.isRegisteredFor("Student2", "ClassC", 2017));
    }
    
    @Test
    public void testDropClassValid2() { //valid case - student registers for two classes and then drops one
    		this.admin.createClass("ClassD", 2017, "InstructorD", 1);
    		this.admin.createClass("ClassE", 2017, "InstructorE", 1);
    		this.student.registerForClass("Student1", "ClassD", 2017);
    		this.student.registerForClass("Student1", "ClassE", 2017);
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassD", 2017));
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassE", 2017));
    		this.student.dropClass("Student1", "ClassD", 2017);
    		assertFalse(this.student.isRegisteredFor("Student1", "ClassD", 2017));
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassE", 2017));
    }
    
    @Test
    public void testDropClassInvalidEnrollment() { //invalid case - student tries to drop a class but isn't enrolled in it and then registers the class
    		this.admin.createClass("ClassF", 2017, "InstructorF", 1);
    		assertFalse(this.student.isRegisteredFor("Student1", "ClassF", 2017));
    		this.student.dropClass("Student1", "ClassF", 2017);
    		
    		this.student.registerForClass("Student1", "ClassF", 2017);
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassF", 2017));
    }
    
//    @Test
//    public void testDropClassInvalidClassEnded() { //invalid case - class has already ended
//		this.admin.createClass("OldClass", 2010, "OldInstructor", 2);
//		this.student.registerForClass("Student1", "OldClass", 2010);
//		this.student.dropClass("Student1", "OldClass", 2010);
//		
//    } //how to test this? (a class in the past shouldn't be made in the first place)
    
    //testing submitHomework()
    
    @Test
    public void testSubmitHomeworkValid() { //valid case - student submits homework for two classes
    	this.admin.createClass("ClassD", 2017, "InstructorD", 1);
		this.admin.createClass("ClassE", 2017, "InstructorE", 1);
		this.student.registerForClass("Student1", "ClassD", 2017);
		this.student.registerForClass("Student1", "ClassE", 2017);
		assertTrue(this.student.isRegisteredFor("Student1", "ClassD", 2017));
		assertTrue(this.student.isRegisteredFor("Student1", "ClassE", 2017));

		this.instructor.addHomework("InstructorD","ClassD", 2017, "hwD");
		this.instructor.addHomework("InstructorE","ClassE", 2017, "hwE");
		this.student.submitHomework("Student1", "hwD", "answerD", "ClassD", 2017);
		this.student.submitHomework("Student1", "hwE", "answerE", "ClassE", 2017);
		assertTrue(this.student.hasSubmitted("Student1", "hwD", "ClassD", 2017));
		assertTrue(this.student.hasSubmitted("Student1", "hwE", "ClassE", 2017));
    }

    @Test
    public void testSubmitHomeworkInvalid() { //invalid case - student submits homework that isn't assigned
    		this.admin.createClass("ClassG", 2017, "InstructorG", 1);

    		this.student.registerForClass("Student1", "ClassG", 2017);
    		this.student.submitHomework("Student1", "hwG", "answerG", "ClassG", 2017);
    		assertFalse(this.student.hasSubmitted("Student1", "hwG", "ClassG", 2017));
    }
    
    @Test
    public void testSubmitHWInvalidNotReg() { //invalid case - student not registered for class
    		this.admin.createClass("ClassH", 2017, "InstructorH", 1);
    		this.admin.createClass("OtherClass", 2017, "InstructorH", 1);
    		this.instructor.addHomework("InstructorH","ClassH", 2017, "hwH");
    		this.student.registerForClass("Student1", "OtherClass", 2017);
    		assertTrue(this.student.isRegisteredFor("Student1", "OtherClass", 2017));
    		
    		this.student.submitHomework("Student1", "hwH", "answerH", "ClassH", 2017);
    		assertFalse(this.student.hasSubmitted("Student1", "hwH", "ClassH", 2017));
    }//Assertion Error: student should not have been able to submit homework because she is not registered for that class

    @Test
    public void testSubmitHWInvalidYear() { //invalid case - class isn't taught in the current year
    		this.admin.createClass("ClassI", 2018, "InstructorI", 1);
    		this.instructor.addHomework("InstructorI","ClassI", 2018, "hwI");
    		this.student.registerForClass("Student1", "ClassI", 2018);
    		assertTrue(this.student.isRegisteredFor("Student1", "ClassI", 2018));
    		
    		this.student.submitHomework("Student1", "hwI", "answerI", "ClassI", 2018);
    		assertFalse(this.student.hasSubmitted("Student1", "hwI", "ClassI", 2018));
    }//Assertion Error: student should not have been able to submit homework because the class year is not the current year
    
}



 
