package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Subject;
import com.thai.doan.dao.repository.DepartmentRepository;
import com.thai.doan.dao.repository.SubjectRepository;
import com.thai.doan.service.SubjectService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepo;
    private final DepartmentRepository departmentRepo;

    @Override
    public List<Subject> getSubjectBySubjectTypeAndDepartment(int subjectType, String departmentName) {
        Optional<Department> department = departmentRepo.findByName(departmentName);
        if (!department.isPresent())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Môn Không Tồn Tại");
        switch (subjectType) {
            case 0:
                return subjectRepo.findBySubjectTypeAndDepartment(
                    Subject.SUBJECT_TYPE.THEORY.ordinal(), department.get());
            case 1:
                return subjectRepo.findBySubjectTypeAndDepartment(
                    Subject.SUBJECT_TYPE.PRACTICE.ordinal(), department.get());
            default:
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Môn Không Tồn Tại");
        }
    }
}
