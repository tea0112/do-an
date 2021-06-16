package com.thai.doan.service;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dto.request.DepartmentAddingRequest;
import com.thai.doan.dto.request.DepartmentUpdatingRequest;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    void add(DepartmentAddingRequest departmentAddingReq);
    void update(Integer id, DepartmentUpdatingRequest departmentUpdatingReq);
}
