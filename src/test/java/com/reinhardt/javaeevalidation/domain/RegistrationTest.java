package com.reinhardt.javaeevalidation.domain;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class RegistrationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private Registration registration;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Before
    public void reinitializeRegistration(){
        registration = new Registration();
        registration.setStudentId("123456789");
        List<Classes> classes = new ArrayList<>();
        classes.add(new Classes());
        registration.setClasses(classes);
        registration.setSemesterStartDate(new Date(System.currentTimeMillis()+100000));
        registration.setStudentName("James Bond");
        registration.setTotalTuition(BigDecimal.valueOf(1000.00));
    }

    @Test
    public void testValid(){
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );

        assertEquals( 0, constraintViolations.size() );
    }

    @Test
    public void testStudentIdGreaterThan9(){
        registration.setStudentId("1234567890");
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Student ID is required to be exactly 9 numbers",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testStudentIdLessThan9(){
        registration.setStudentId("12345678");
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Student ID is required to be exactly 9 numbers",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testStudentIdContainsInvalidCharacters(){
        registration.setStudentId("1234567A9");
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Student ID can only contain numbers",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testStudentIdIsNotNull(){
        registration.setStudentId(null);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Student ID cannot be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testClassesIsEmpty(){
        registration.setClasses(new ArrayList<>());
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "You cannot register for less than 1 class or more than 6 classes at a time",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testClassesIsGreaterThan6(){
        List<Classes> classes = new ArrayList<>();
        for(int i=0;i<7;i++){
            classes.add(new Classes());
        }
        registration.setClasses(classes);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "You cannot register for less than 1 class or more than 6 classes at a time",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testClassesIsNotNull(){
        registration.setClasses(null);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Classes cannot be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testStudentNameNull(){
        registration.setStudentName(null);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Student name cannot be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testStudentNameLargerThan50Characters(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<51;i++){
            stringBuilder.append("a");
        }
        registration.setStudentName(stringBuilder.toString());
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Student name cannot be greater than 50 characters",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testStudentNameSpecialCharacters(){
        registration.setStudentName("$p3c14l");
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Student name can only contain alpha characters and spaces",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testStartDateNull(){
        registration.setSemesterStartDate(null);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Semester start date cannot be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testPreviousDate(){
        registration.setSemesterStartDate(new Date(System.currentTimeMillis() - 100000));
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Semester start date has to be in the future",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testCurrentDate(){
        registration.setSemesterStartDate(new Date(System.currentTimeMillis()));
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Semester start date has to be in the future",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testTotalTuitionNull(){
        registration.setTotalTuition(null);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Total tuition cannot be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testInteger7Digits(){
        registration.setTotalTuition(BigDecimal.valueOf(1000000.00));
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Total tuition cannot be more than 999999.99",
                constraintViolations.iterator().next().getMessage()
        );
    }

}
