package com.thai.doan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class StudentUpdatingRequest {
    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private String place;

    @Pattern(regexp = "((09|03|07|08|05)+([0-9]{8})\\b)", message = "lỗi định dạng số điện thoại")
    private String phoneNumber;
}
