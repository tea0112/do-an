package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class StudyUpdateRequest {
    @Min(0)
    private Float grade;

    @Min(0)
    private Integer subjectId;
}
