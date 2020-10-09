package com.reinhardt.javaeevalidation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Registration {

    @NotNull(message = "Student ID cannot be null")
    @Pattern(regexp = "[0-9]*", message = "Student ID can only contain numbers")
    @Size(min=9, max=9, message = "Student ID is required to be exactly 9 numbers")
    @Getter @Setter
    private String studentId;

    @NotNull(message = "Student name cannot be null")
    @Pattern(regexp = "[a-zA-Z ]*", message = "Student name can only contain alpha characters and spaces")
    @Size(max=50, message = "Student name cannot be greater than 50 characters")
    @Getter @Setter
    private String studentName;

    @NotNull(message = "Classes cannot be null")
    @Size(min=1, max=6, message = "You cannot register for less than 1 class or more than 6 classes at a time")
    @Getter @Setter
    private List<Classes> classes;

    @NotNull(message = "Semester start date cannot be null")
    @Future(message = "Semester start date has to be in the future")
    @Getter @Setter
    private Date semesterStartDate;

    @NotNull(message = "Total tuition cannot be null")
    @Digits(integer = 6, fraction = 2, message = "Total tuition cannot be more than 999999.99")
    @Getter @Setter
    private BigDecimal totalTuition;

}
