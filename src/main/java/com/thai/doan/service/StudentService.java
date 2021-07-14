package com.thai.doan.service;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.User;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.dto.request.PasswordChangeRequest;
import com.thai.doan.dto.request.StudentUpdatingRequest;

import java.util.List;

public interface StudentService {
    Student createNewStudent(NewStudentRequest newStudentRequest);
    Student updateWithId(StudentUpdatingRequest studentUpdatingReq, String id);
    Student getWithId(String studentId);
    Student getAuthenticated();
    void delete(Integer studentId);
    List<Student> getWithSession(Integer sessionId);
    User updatePassword(PasswordChangeRequest passwordChangeReq);
}
