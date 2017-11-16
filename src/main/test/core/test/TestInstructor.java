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
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }
    //Definitinons for this given test suite, this allows you to define now so u don't have to keep re-defining for each test
    //Test suite:the context/environment 

    
    @Test
    public void testValidAddHW() {//valid case: instructor adds two homework assignments
        this.admin.createClass("hwClass", 2017, "InstructorV", 5);
        this.instructor.addHomework("InstructorV","hwClass", 2017, "hw1");
        this.instructor.addHomework("InstructorV","hwClass", 2017, "hw2");
        assertTrue(this.instructor.homeworkExists("hwClass", 2017,"hw1"));
        assertTrue(this.instructor.homeworkExists("hwClass", 2017,"hw2"));
    }
    
    @Test
    public void testInvalidAddHW() {
        this.admin.createClass("hwClass", 2017, "RightInstructor", 5);
        this.instructor.addHomework("WrongInstructor","hwClass", 2017, "hw1");
        assertFalse(this.instructor.homeworkExists("hwClass", 2017,"hw1"));
    }//assertion error: hw should not exist because incorrect instructor was passed in
    
    //tests for assignGrade()
    
    @Test 
    public void testValidAssignGrade() { //valid scenario
    		this.admin.createClass("HWClass1", 2017, "InstructorG", 5);
        this.instructor.addHomework("InstructorG","HWClass1", 2017, "hw1");
        this.student.registerForClass("Student1", "HWClass1", 2017);
        this.student.submitHomework("Student1", "hw1", "answer", "HWClass1", 2017);
        this.instructor.assignGrade("InstructorG","HWClass1", 2017, "hw1", "Student", 60);
        assertTrue( this.instructor.getGrade("HWClass1", 2017, "hw1", "Student") == 60);
    }
    
    @Test 
    public void testInvalidAssignGradeWrongInstr() { //case: different (wrong) instructor tries to assign a grade
    		this.admin.createClass("HWClass2", 2017, "SomeInstructor", 5);
        this.instructor.addHomework("SomeInstructor","HWClass2", 2017, "hw1");
        this.student.registerForClass("Student2", "HWClass2", 2017);
        this.student.submitHomework("Student1", "hw1", "answer", "HWClass1", 2017);
        this.instructor.assignGrade("WrongInstructor","HWClass2", 2017, "hw1", "Student2", 80);
        assertFalse( this.instructor.getGrade("HWClass2", 2017, "hw1", "Student2") == 80);
    }//assertion error: grade should not exist because wrong instructor assigned it
    
    @Test 
    public void testInvalidAssignGradeNoHW() {//case: given homework was not assigned
    		this.admin.createClass("HWClass3", 2017, "InstructorN", 5);

    		this.student.registerForClass("Student3", "HWClass3", 2017);
    		this.student.submitHomework("Student1", "hw1", "answer", "HWClass1", 2017);
        this.instructor.assignGrade("InstructorN","HWClass3", 2017, "hw1", "Student3", 82);
        assertTrue( this.instructor.getGrade("HWClass3", 2017, "hw1", "Student3") == null);
    }
    
    @Test 
    public void testInvalidAssignGradeStudent() { //case: student hasn't submitted hw
    		this.admin.createClass("HWClass4", 2017, "InstructorS", 5);
        this.instructor.addHomework("InstructorS","HWClass4", 2017, "hw1");
        this.student.registerForClass("Student4", "HWClass4", 2017);
        
        this.instructor.assignGrade("InstructorS","HWClass4", 2017, "hw1", "Student4", 80);
        assertTrue( this.instructor.getGrade("HWClass4", 2017, "hw1", "Student4") == null);
    }//assertion error: grade shouldn't exist because student hasn't submitted hw
    
}



 
