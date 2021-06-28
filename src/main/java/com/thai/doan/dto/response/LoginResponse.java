package com.thai.doan.dto.response;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String refreshToken;
    private String accessToken;
    private Student student;
    private User user;
}
