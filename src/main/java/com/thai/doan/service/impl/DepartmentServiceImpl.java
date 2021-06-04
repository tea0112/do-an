package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.repository.DepartmentRepository;
import com.thai.doan.service.DepartmentService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepo;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }
}

