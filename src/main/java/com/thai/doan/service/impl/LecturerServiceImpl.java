package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Lecturer;
import com.thai.doan.dao.repository.DepartmentRepository;
import com.thai.doan.dao.repository.LecturerRepository;
import com.thai.doan.service.LecturerService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepository lecturerRepo;
    private final DepartmentRepository departmentRepo;

    @Override
    public List<Lecturer> getAllLecturer(String department) {
        Optional<Department> departmentOpt = departmentRepo.findByName(department);
        return lecturerRepo.findAllByDepartment(departmentOpt.get());
    }

    @Override
    public List<Lecturer> getWithDepartmentId(String departmentId) {
        Optional<Department> department = departmentRepo.findById(Integer.parseInt(departmentId));
        if (!department.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return lecturerRepo.findAllByDepartment(department.get());
    }
}
