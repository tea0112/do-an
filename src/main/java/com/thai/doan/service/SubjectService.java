package com.thai.doan.service;

import com.thai.doan.dao.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getSubjectBySubjectTypeAndDepartment(int subjectType, String departmentName);
    List<Subject> getBySubjectTypeAndDepartment(int subjectType, int departmentId);
}
