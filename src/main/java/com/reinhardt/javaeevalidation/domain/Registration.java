package com.reinhardt.javaeevalidation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Registration {

    @NotNull
    @Pattern(regexp = "[0-9]*")
    @Size(min=9, max=9)
    @Getter @Setter
    private String studentId;

    @NotNull
    @Pattern(regexp = "[a-zA-Z ]*")
    @Size(max=50)
    @Getter @Setter
    private String studentName;

    @NotNull
    @Size(min=1, max=6)
    @Getter @Setter
    private List<Classes> classes;

    @Future
    @Getter @Setter
    private Date semesterStartDate;

    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Getter @Setter
    private BigDecimal totalTuition;

}
