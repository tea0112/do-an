package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Subject;
import com.thai.doan.dao.repository.DepartmentRepository;
import com.thai.doan.dao.repository.SubjectRepository;
import com.thai.doan.dto.request.SubjectAddingRequest;
import com.thai.doan.dto.request.SubjectUpdatingRequest;
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

    @Override
    public List<Subject> getBySubjectTypeAndDepartment(int subjectType, int departmentId) {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        switch (subjectType) {
            case 0:
                return subjectRepo.findBySubjectTypeAndDepartment(
                    Subject.SUBJECT_TYPE.THEORY.ordinal(), department.get());
            case 1:
                return subjectRepo.findBySubjectTypeAndDepartment(
                    Subject.SUBJECT_TYPE.PRACTICE.ordinal(), department.get());
            default:
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void addOne(SubjectAddingRequest subjectAddingRequest) {
        try {
            Department department = departmentRepo.findById(Integer.parseInt(subjectAddingRequest.getDepartmentId()))
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
                );
            Subject subject = Subject.builder()
                .name(subjectAddingRequest.getName())
                .subjectType(subjectAddingRequest.getSubjectType())
                .department(department)
                .build();
            subjectRepo.save(subject);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public void updateWithId(Integer id, SubjectUpdatingRequest subjectUpdatingReq) {
        try {
            Department department = departmentRepo.findById(subjectUpdatingReq.getDepartmentId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Subject subject = Subject.builder()
                .id(id)
                .name(subjectUpdatingReq.getName())
                .subjectType(subjectUpdatingReq.getSubjectType())
                .department(department)
                .build();
            subjectRepo.save(subject);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }
}
