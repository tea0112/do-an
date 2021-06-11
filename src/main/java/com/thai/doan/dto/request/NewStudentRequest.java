package com.thai.doan.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class NewStudentRequest {
    @Size(min = 1, max = 100, message = "Tối đa 100 ký tự")
    @NotBlank(message = "Không được để trống")
    private String username;

    @Size(min = 8, max = 20, message = "Từ 8 đến 20 ký tự")
    @NotBlank(message = "Không được để trống")
    private String password;

    @Size(min = 8, max = 20, message = "Từ 8 đến 20 ký tự")
    @NotBlank(message = "Không được để trống")
    private String repassword;

    @NotBlank(message = "Không được để trống")
    private String firstName;

    @NotBlank(message = "Không được để trống")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Lỗi định dạng")
    private LocalDate birth;

    @NotBlank(message = "Không được để trống")
    private String place;

    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^[0-9]+$", message = "Lỗi định dạng")
    private String phoneNumber;

    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Lỗi định dạng")
    private String sessionName;
}
