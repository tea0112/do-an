package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ClassroomUpdatingRequest {
    @NotBlank
    private String name;
    @Min(0)
    private Integer lectureHallId;
}
