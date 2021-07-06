package com.thai.doan.service;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.StudentClassRelation;

import java.util.List;

public interface StudentClassRelationService {
    List<Student> getWithClassId(String classId);
    Student addStudentToClass(int studentId, int classId);
    void removeStudentFromClass(int studentId, int classId);
    List<StudentClassRelation> getWithStudentId(Integer studentId);
}
