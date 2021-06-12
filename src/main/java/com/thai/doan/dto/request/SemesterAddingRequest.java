package com.thai.doan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class SemesterAddingRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDay;
    @Min(1)
    private Integer termNumber;
    @Min(1)
    private Integer sessionId;
}
