package com.thai.doan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class StudentUpdatingRequest {
    @NotBlank
    @Length(min = 1)
    private String lastName;

    @NotBlank
    @Length(min = 1)
    private String firstName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @NotBlank
    private String place;

    @Pattern(regexp = "((01|02|03|04|05|06|07|08|09)+([0-9]{8})\\b)", message = "lỗi định dạng số điện thoại")
    @Length(min = 10, max = 10)
    @NotBlank
    private String phoneNumber;

    @NotNull
    private Boolean gender;
}
