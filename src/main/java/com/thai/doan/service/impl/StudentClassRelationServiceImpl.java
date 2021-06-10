package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.StudentClassRelation;
import com.thai.doan.dao.repository.ClassesRepository;
import com.thai.doan.dao.repository.StudentClassRelationRepository;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.exception.ErrorCode;
import com.thai.doan.service.StudentClassRelationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class StudentClassRelationServiceImpl implements StudentClassRelationService {
    private final StudentClassRelationRepository studentClassRelationRepo;
    private final StudentRepository studentRepo;
    private final ClassesRepository classesRepo;

    @Override
    public List<Student> getWithClassId(String classId) {
        Classes clazz = classesRepo.findById(Integer.parseInt(classId)).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.CLASS_NOT_FOUND)
        );

        List<StudentClassRelation> studentClassRelations = studentClassRelationRepo.findByClassId(clazz);
        if (studentClassRelations == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.STUDENT_CLASS_RELATION_NOT_FOUND);
        }

        return studentClassRelations
            .stream()
            .map(StudentClassRelation::getStudentId)
            .collect(Collectors.toList());

    }
}
