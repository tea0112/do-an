package com.thai.doan.controller;

import com.thai.doan.dao.model.Subject;
import com.thai.doan.service.SubjectService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Data
public class SubjectController {
    private final SubjectService subjectSv;

    @GetMapping("/admin/subject")
    public @ResponseBody List<Subject> getSubjectsWithDepartment(
        @RequestParam int subjectType,
        @RequestParam String department) {
        return subjectSv.getSubjectBySubjectTypeAndDepartment(subjectType, department);
    }
}
