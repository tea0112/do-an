package com.thai.doan.controller;

import com.thai.doan.dao.model.Subject;
import com.thai.doan.service.SubjectService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/admin/subject", method = RequestMethod.GET, params = {"subjectType", "departmentId"})
    public List<Subject> getSubjectsWithDepartment(@RequestParam int subjectType, @RequestParam int departmentId) {
        return subjectSv.getBySubjectTypeAndDepartment(subjectType, departmentId);
    }
}
