package com.thai.doan.service;

import com.thai.doan.dao.model.Student;

import java.util.List;

public interface StudentClassRelationService {
    List<Student> getWithClassId(String classId);
    void addStudentToClass(int studentId, int classId);
    void removeStudentFromClass(int studentId, int classId);
}
