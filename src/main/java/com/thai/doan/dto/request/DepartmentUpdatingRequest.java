package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentUpdatingRequest {
    @Min(0)
    private Integer id;
    @NotBlank
    private String name;
    @NotNull
    private Boolean isGeneral;
}
