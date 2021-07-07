package com.thai.doan.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Min(0)
    private int subject;

    @Min(0)
    private int department;

    @Min(0)
    private int lecturer;

    @Min(0)
    private int session;

    @Min(0)
    private int classId;

    @Min(0)
    private int semester;

    @Min(1)
    private int startPeriod;

    @Min(1)
    private int endPeriod;

    @Min(2)
    @Max(8)
    private int week;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Không được trống")
    private LocalDate startDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Không được trống")
    private LocalDate endDay;

    @Min(0)
    private int periodType;

    @Min(0)
    private int classroomId;
}
