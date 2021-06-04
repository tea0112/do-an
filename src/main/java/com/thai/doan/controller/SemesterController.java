package com.thai.doan.controller;


import com.thai.doan.dao.model.Semester;
import com.thai.doan.service.SemesterService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
public class SemesterController {
    private final SemesterService semesterSv;

    @GetMapping("/admin/semesters")
    public List<Semester> getWithSession(@RequestParam int sessionId) {
        return semesterSv.getWithSession(sessionId);
    }
}
