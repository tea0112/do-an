package com.thai.doan.service;

import com.thai.doan.dto.request.NewStudentRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface StudentService {
    ModelAndView createNewStudent(NewStudentRequest newStudentRequest, BindingResult result);
}
