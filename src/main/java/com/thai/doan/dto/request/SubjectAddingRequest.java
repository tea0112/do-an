package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class SubjectAddingRequest {
    @NotBlank
    private String name;

    @Min(0)
    private int subjectType;

    @NotBlank
    private String departmentId;
}
