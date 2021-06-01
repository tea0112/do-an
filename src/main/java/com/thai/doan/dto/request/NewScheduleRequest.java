package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class NewScheduleRequest {
    @Min(0)
    @Max(1)
    private int subjectType;

    @NotBlank
    private String subject;

    @NotBlank
    private String department;

    @NotBlank
    private String lecturer;

    @NotBlank
    private String session;

    @NotBlank
    private String className;

    @Min(1)
    private int startPeriod;

    @Min(1)
    private int endPeriod;

    @Min(2)
    private int week;

    @NotNull
    private LocalDate startDay;

    @NotNull
    private LocalDate endDay;

    @Min(0)
    private int periodType;
}
