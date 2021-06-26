package com.thai.doan.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String refreshToken;
    private String accessToken;
}
