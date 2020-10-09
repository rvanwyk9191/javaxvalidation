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
        /*assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );*/
    }

    @Test
    public void testStudentIdGreaterThan9(){
        registration.setStudentId("1234567890");
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
    }

    @Test
    public void testStudentIdLessThan9(){
        registration.setStudentId("12345678");
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
    }

    @Test
    public void testStudentIdContainsInvalidCharacters(){
        registration.setStudentId("1234567A9");
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
    }

    @Test
    public void testStudentIdIsNotNull(){
        registration.setStudentId(null);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
    }

    @Test
    public void testClassesIsEmpty(){
        registration.setClasses(new ArrayList<>());
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
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
    }

    @Test
    public void testClassesIsNotNull(){
        registration.setClasses(null);
        Set<ConstraintViolation<Registration>> constraintViolations =
                validator.validate( registration );
        assertEquals( 1, constraintViolations.size() );
    }

}
