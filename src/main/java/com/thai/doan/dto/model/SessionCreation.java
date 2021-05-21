package com.thai.doan.dto.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SessionCreation {
    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Chỉ cho phép là chữ hoặc số")
    private String name;
}
