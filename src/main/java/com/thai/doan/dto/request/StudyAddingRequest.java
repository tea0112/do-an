package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class StudyAddingRequest {
  @Min(0)
  private Float grade;

  @Min(0)
  private Integer subjectId;

  @Min(0)
  private Integer semesterId;

  @Min(0)
  private Integer studentId;
}
