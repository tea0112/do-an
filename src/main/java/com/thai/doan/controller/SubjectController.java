package com.thai.doan.controller;

import com.thai.doan.dao.model.Subject;
import com.thai.doan.dto.request.SubjectAddingRequest;
import com.thai.doan.dto.request.SubjectUpdatingRequest;
import com.thai.doan.service.SubjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@Data
public class SubjectController {
    private final SubjectService subjectSv;

    // RestFul api
    @GetMapping("/admin/subject")
    public @ResponseBody
    List<Subject> getSubjectsWithDepartment(
        @RequestParam int subjectType,
        @RequestParam String department) {
        return subjectSv.getSubjectBySubjectTypeAndDepartment(subjectType, department);
    }

    @RequestMapping(
        value = "/api/admin/subjects",
        method = RequestMethod.GET,
        params = {"subjectType", "departmentId"})
    public List<Subject> getWithDepartment(@RequestParam int subjectType,
                                           @RequestParam int departmentId) {
        return subjectSv.getBySubjectTypeAndDepartment(subjectType, departmentId);
    }

    @PostMapping("/admin/subjects")
    public ResponseEntity<?> add(@RequestBody @Valid SubjectAddingRequest subjectAddingReq) {
        subjectSv.addOne(subjectAddingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api/admin/subjects/{id}")
    public ResponseEntity<?> updateWithId(@PathVariable Integer id,
                                          @RequestBody SubjectUpdatingRequest subjectUpdatingReq) {
        subjectSv.updateWithId(id, subjectUpdatingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/subjects/{id}")
    public ResponseEntity<?> updateWithId(@PathVariable Integer id) {
        subjectSv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
