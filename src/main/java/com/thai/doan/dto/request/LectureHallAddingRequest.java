package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LectureHallAddingRequest {
  @NotBlank
  private String name;

  @NotNull
  private String address;
}
