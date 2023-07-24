package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SessionUpdatingRequest {
  @NotNull
  private String name;
}
