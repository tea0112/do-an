package com.thai.doan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ScheduleUpdatingRequest {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDay;
    @Min(0)
    private int weekDay;
    @Min(0)
    private int periodType;
    @Min(0)
    private Integer startPeriod;
    @Min(0)
    private Integer endPeriod;
    @Min(0)
    private Integer semester;
    @Min(0)
    private Integer lecturer;
    @Min(0)
    private Integer subject;
    @Min(0)
    private Integer classes;
    @Min(0)
    private Integer classroomId;
}
