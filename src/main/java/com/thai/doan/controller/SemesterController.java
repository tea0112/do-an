package com.thai.doan.controller;


import com.thai.doan.dao.model.Semester;
import com.thai.doan.dto.request.SemesterAddingRequest;
import com.thai.doan.service.SemesterService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Data
@RestController
public class SemesterController {
    private final SemesterService semesterSv;

    //view
    @GetMapping("/admin/hoc-ky/them")
    public ModelAndView getAdd() {
        return new ModelAndView("admin/semester/add-semester");
    }

    // restful api
    @GetMapping("/api/semesters")
    public List<Semester> getWithSession(@RequestParam int sessionId) {
        return semesterSv.getWithSession(sessionId);
    }

    @PostMapping("/api/admin/semesters")
    public ResponseEntity<?> add(@Valid @RequestBody SemesterAddingRequest semesterAddingReq) {
        semesterSv.add(semesterAddingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
