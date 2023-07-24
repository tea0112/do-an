package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class StudentClassAddingRequest {
  @Min(0)
  private Integer studentId;
  @Min(0)
  private Integer classId;
}
