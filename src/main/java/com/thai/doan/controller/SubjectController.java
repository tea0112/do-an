package com.thai.doan.controller;

import com.thai.doan.dao.model.Subject;
import com.thai.doan.dto.request.SubjectAddingRequest;
import com.thai.doan.service.SubjectService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@Data
public class SubjectController {
    private final SubjectService subjectSv;

    // view
    @GetMapping("/admin/mon/them")
    public ModelAndView getAddPage() {
        return new ModelAndView("admin/subject/add-subject");
    }

    // RestFul api
    @GetMapping("/admin/subject")
    public @ResponseBody
    List<Subject> getSubjectsWithDepartment(
        @RequestParam int subjectType,
        @RequestParam String department) {
        return subjectSv.getSubjectBySubjectTypeAndDepartment(subjectType, department);
    }

    @RequestMapping(value = "/admin/subject", method = RequestMethod.GET, params = {"subjectType", "departmentId"})
    public List<Subject> getSubjectsWithDepartment(@RequestParam int subjectType, @RequestParam int departmentId) {
        return subjectSv.getBySubjectTypeAndDepartment(subjectType, departmentId);
    }

    @PostMapping("/admin/subject/add")
    public ResponseEntity<?> add(@RequestBody @Valid SubjectAddingRequest subjectAddingReq) {
        subjectSv.addOne(subjectAddingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
