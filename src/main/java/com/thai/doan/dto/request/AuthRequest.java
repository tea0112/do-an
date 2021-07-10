package com.thai.doan.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest {
    @NotBlank
    String bearerToken;
}
