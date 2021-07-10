package com.thai.doan.controller;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.StudentClassRelation;
import com.thai.doan.dto.request.StudentClassAddingRequest;
import com.thai.doan.service.StudentClassRelationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@Data
public class StudentClassController {
    private final StudentClassRelationService studentClassRelationSv;

    // restful api
    @PostMapping("/api/admin/studentClass")
    public ResponseEntity<Student> add(@RequestBody @Valid StudentClassAddingRequest studentClassAddingReq) {
        return new ResponseEntity<>(
            studentClassRelationSv.addStudentToClass(studentClassAddingReq.getStudentId(),
            studentClassAddingReq.getClassId()), HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/studentClass")
    public ResponseEntity<?> deleteWithId(@RequestParam int studentId, @RequestParam int classId) {
        studentClassRelationSv.removeStudentFromClass(studentId, classId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/studentClass")
    public List<StudentClassRelation> getWithStudentId(@RequestParam int studentId) {
        return studentClassRelationSv.getWithStudentId(studentId);
    }

}
