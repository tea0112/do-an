package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LecturerAddingRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String departmentId;
}
