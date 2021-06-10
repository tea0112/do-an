package com.thai.doan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
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

    private String phoneNumber;
}
