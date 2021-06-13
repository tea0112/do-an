package com.thai.doan.service;

import com.thai.doan.dao.model.Subject;
import com.thai.doan.dto.request.SubjectAddingRequest;
import com.thai.doan.dto.request.SubjectUpdatingRequest;

import java.util.List;

public interface SubjectService {
    List<Subject> getSubjectBySubjectTypeAndDepartment(int subjectType, String departmentName);
    List<Subject> getBySubjectTypeAndDepartment(int subjectType, int departmentId);
    void addOne(SubjectAddingRequest subjectAddingRequest);
    void updateWithId(Integer id, SubjectUpdatingRequest subjectUpdatingReq);
}
