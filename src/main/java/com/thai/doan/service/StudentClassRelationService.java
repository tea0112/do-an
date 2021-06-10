package com.thai.doan.service;

import com.thai.doan.dao.model.Student;

import java.util.List;

public interface StudentClassRelationService {
    List<Student> getWithClassId(String classId);
}
