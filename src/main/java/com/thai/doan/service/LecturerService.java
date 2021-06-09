package com.thai.doan.service;

import com.thai.doan.dao.model.Lecturer;

import java.util.List;

public interface LecturerService {
    List<Lecturer> getAllLecturer(String department);
    List<Lecturer> getWithDepartmentId(String departmentId);
}
