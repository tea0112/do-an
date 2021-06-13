package com.thai.doan.controller;


import com.thai.doan.dao.model.Semester;
import com.thai.doan.dto.request.SemesterAddingRequest;
import com.thai.doan.dto.request.SemesterUpdatingRequest;
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
    public ModelAndView getAddPage() {
        return new ModelAndView("admin/semester/add-semester");
    }

    @GetMapping("/admin/hoc-ky/sua")
    public ModelAndView getEditPage() {
        return new ModelAndView("admin/semester/edit-semester");
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

    @PatchMapping("/api/admin/semesters/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody SemesterUpdatingRequest semesterUpdatingReq,
                                    @PathVariable Integer id) {
        semesterSv.updateWithId(semesterUpdatingReq, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
