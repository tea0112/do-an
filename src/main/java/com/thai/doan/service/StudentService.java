package com.thai.doan.service;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.dto.request.StudentUpdatingRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface StudentService {
    ModelAndView createNewStudent(NewStudentRequest newStudentRequest, BindingResult result);
    void updateWithId(StudentUpdatingRequest studentUpdatingReq, String id);
    Student getWithId(String studentId);
    Student getAuthenticated();
    void delete(Integer studentId);
    List<Student> getWithSession(Integer sessionId);
}
