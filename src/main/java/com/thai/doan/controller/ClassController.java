package com.thai.doan.controller;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.service.ClassesService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
public class ClassController {
    private final ClassesService classSv;

    // api
    @GetMapping("/admin/classes")
    public List<Classes> getWithDepartmentAndSession(
        @RequestParam(name = "department") String departmentName, @RequestParam(name = "session") int sessionId) {
        return classSv.getWithDepartmentAndSession(departmentName, sessionId);
    }

    @GetMapping("/api/classes")
    public List<Classes> getWithSessionIdAndDepartmentId(@RequestParam int session, @RequestParam int department) {
        return classSv.getWithSessionIdAndDepartmentId(session, department);
    }
}
