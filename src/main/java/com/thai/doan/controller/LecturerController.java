package com.thai.doan.controller;

import com.thai.doan.dao.model.Lecturer;
import com.thai.doan.service.LecturerService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Data
public class LecturerController {
    private final LecturerService lecturerSv;
    // curd

    //api
    @GetMapping("/admin/lecturers")
    public @ResponseBody List<Lecturer> getAllLecturerWithDepartment(@RequestParam String department) {
        return lecturerSv.getAllLecturer(department);
    }
}
