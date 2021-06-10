package com.thai.doan.controller;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.dto.request.StudentUpdatingRequest;
import com.thai.doan.service.SessionService;
import com.thai.doan.service.StudentClassRelationService;
import com.thai.doan.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final SessionService sessionSv;
    private final StudentService studentService;
    private final StudentClassRelationService studentClassRelationSv;

    // admin view
    @GetMapping("/admin/sinh-vien/them")
    public ModelAndView addStudent() {
        ModelAndView mvc = new ModelAndView("admin/student/add-student");
        mvc.addObject("sessionNames", sessionSv.getAllSession());
        mvc.addObject("newStudentRequest", new NewStudentRequest());
        return mvc;
    }

    @GetMapping("/admin/sinh-vien/sua")
    public ModelAndView editStudent() {
        return new ModelAndView("admin/student/edit-student");
    }

    @RequestMapping(value = "/admin/sinh-vien/sua", params = "studentId", method = RequestMethod.GET)
    public ModelAndView editStudentWithId(@RequestParam int studentId) {
        return new ModelAndView("admin/student/edit-student");
    }

    //curd
    @PostMapping("/admin/add-student")
    public ModelAndView addNewStudent(@Valid NewStudentRequest stdReq, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mvc = new ModelAndView("admin/student/add-student", result.getModel());
            mvc.addObject("sessionNames", sessionSv.getAllSession());
            mvc.addObject("newStudentRequest", new NewStudentRequest());
            return mvc;
        }
        return studentService.createNewStudent(stdReq, result);
    }

    // restful api
    @GetMapping("/api/admin/students")
    public List<Student> getWithClass(@RequestParam String classId) {
        return studentClassRelationSv.getWithClassId(classId);
    }

    @PatchMapping("/api/sinh-vien/sua")
    public ResponseEntity<?> updateWithId(@RequestBody StudentUpdatingRequest studentUpdatingReq,
                                          @RequestParam String studentId) {
        studentService.updateWithId(studentUpdatingReq, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
