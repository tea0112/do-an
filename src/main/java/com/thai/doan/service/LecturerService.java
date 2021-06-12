package com.thai.doan.service;

import com.thai.doan.dao.model.Lecturer;
import com.thai.doan.dto.request.LecturerAddingRequest;

import java.util.List;

public interface LecturerService {
    List<Lecturer> getAllLecturer(String department);
    List<Lecturer> getWithDepartmentId(String departmentId);
    void add(LecturerAddingRequest lecturerAddingReq);
}
