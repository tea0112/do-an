package com.thai.doan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    @Min(0)
    Integer studentId;
    @NotNull
    String password;
}
