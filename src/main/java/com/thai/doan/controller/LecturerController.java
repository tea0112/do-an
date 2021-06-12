package com.thai.doan.controller;

import com.thai.doan.dao.model.Lecturer;
import com.thai.doan.dto.request.LecturerAddingRequest;
import com.thai.doan.service.LecturerService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@Data
public class LecturerController {
    private final LecturerService lecturerSv;

    // view
    @GetMapping("/admin/giang-vien/them")
    public ModelAndView getAdd() {
        ModelAndView mav = new ModelAndView("admin/lecturer/add-lecturer");
        return mav;
    }

    //Restful api
    @GetMapping("/admin/lecturers")
    public @ResponseBody List<Lecturer> getAllLecturerWithDepartment(@RequestParam String department) {
        return lecturerSv.getAllLecturer(department);
    }

    @RequestMapping(value = "/api/admin/lecturers", method = RequestMethod.GET, params = "departmentId")
    public @ResponseBody List<Lecturer> getWithDepartmentId(@RequestParam String departmentId) {
        return lecturerSv.getWithDepartmentId(departmentId);
    }

    @PostMapping("/api/admin/lecturers")
    public ResponseEntity<?> add(@RequestBody @Valid LecturerAddingRequest lecturerAddingReq) {
        lecturerSv.add(lecturerAddingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
