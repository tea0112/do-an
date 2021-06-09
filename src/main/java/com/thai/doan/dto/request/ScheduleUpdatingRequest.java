package com.thai.doan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleUpdatingRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDay;
    private int weekDay;
    private int periodType;
    private Integer startPeriod;
    private Integer endPeriod;
    private Integer semester;
    private Integer lecturer;
    private Integer subject;
    private Integer classes;
}
