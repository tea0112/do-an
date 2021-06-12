package com.thai.doan.dto.request;

import lombok.Data;

@Data
public class ClassUpdatingRequest {
    private String name;
    private Integer classType;
    private Integer sessionId;
    private Integer departmentId;
}
