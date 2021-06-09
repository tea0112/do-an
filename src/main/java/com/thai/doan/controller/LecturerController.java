package com.thai.doan.controller;

import com.thai.doan.dao.model.Lecturer;
import com.thai.doan.service.LecturerService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
public class LecturerController {
    private final LecturerService lecturerSv;
    // curd

    //Restful api
    @GetMapping("/admin/lecturers")
    public @ResponseBody List<Lecturer> getAllLecturerWithDepartment(@RequestParam String department) {
        return lecturerSv.getAllLecturer(department);
    }

    @RequestMapping(value = "/api/admin/lecturers", method = RequestMethod.GET, params = "departmentId")
    public @ResponseBody List<Lecturer> getWithDepartmentId(@RequestParam String departmentId) {
        return lecturerSv.getWithDepartmentId(departmentId);
    }
}
