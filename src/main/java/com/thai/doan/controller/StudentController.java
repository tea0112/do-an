package com.thai.doan.controller;

import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.service.SessionService;
import com.thai.doan.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final SessionService sessionSv;
    private final StudentService studentService;

    //page
    @GetMapping("/admin/add-student")
    public ModelAndView addSinhVien() {
        ModelAndView mvc = new ModelAndView("admin/student/add-student");
        mvc.addObject("sessionNames", sessionSv.getAllSessionName());
        mvc.addObject("newStudentRequest", new NewStudentRequest());
        return mvc;
    }

    //curd
    @PostMapping("/admin/add-student")
    public ModelAndView addNewStudent(@Valid NewStudentRequest stdReq, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mvc = new ModelAndView("admin/student/add-student", result.getModel());
            mvc.addObject("sessionNames", sessionSv.getAllSessionName());
            mvc.addObject("newStudentRequest", new NewStudentRequest());
            return mvc;
        }
        return studentService.createNewStudent(stdReq, result);
    }

}
