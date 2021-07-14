package com.thai.doan.controller;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.User;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.dto.request.PasswordChangeRequest;
import com.thai.doan.dto.request.StudentUpdatingRequest;
import com.thai.doan.service.SessionService;
import com.thai.doan.service.StudentClassRelationService;
import com.thai.doan.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentClassRelationService studentClassRelationSv;

    // restful api
    @PostMapping("/api/admin/student/add")
    public Student addNewStudent(@RequestBody @Valid NewStudentRequest stdReq) {
        return studentService.createNewStudent(stdReq);
    }

    @GetMapping("/api/admin/students")
    public List<Student> getWithClass(@RequestParam String classId) {
        return studentClassRelationSv.getWithClassId(classId);
    }

    @GetMapping("/api/admin/students/{id}")
    public Student getWithId(@PathVariable String id) {
        return studentService.getWithId(id);
    }

    @RequestMapping(value = "/api/students/{studentId}", method = RequestMethod.PATCH)
    public ResponseEntity<Student> updateWithId(@RequestBody StudentUpdatingRequest studentUpdatingReq,
                                                @PathVariable String studentId) {
        return new ResponseEntity<>(studentService.updateWithId(studentUpdatingReq, studentId), HttpStatus.OK);
    }

    @GetMapping("/api/students/current")
    public Student getCurrent() {
        return studentService.getAuthenticated();
    }

    @GetMapping("/api/students")
    public List<Student> getWithSession(@RequestParam Integer sessionId) {
        return studentService.getWithSession(sessionId);
    }

    @DeleteMapping("/api/students/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api/students/passwordChange")
    public ResponseEntity<User> updatePassword(@RequestBody @Valid PasswordChangeRequest passwordChangeReq) {
        return new ResponseEntity<>(studentService.updatePassword(passwordChangeReq), HttpStatus.OK);
    }

}
