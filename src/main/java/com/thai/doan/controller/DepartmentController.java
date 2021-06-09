package com.thai.doan.controller;

import com.thai.doan.dao.model.Department;
import com.thai.doan.service.DepartmentService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
public class DepartmentController {
    private final DepartmentService departmentService;

    // RESTFul api
    @GetMapping("/api/departments")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

}
