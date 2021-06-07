package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.ClassesRepository;
import com.thai.doan.dao.repository.DepartmentRepository;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.service.ClassesService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class ClassesServiceImpl implements ClassesService {
    private final ClassesRepository classesRepo;
    private final DepartmentRepository departmentRepo;
    private final SessionRepository sessionRepo;

    @Override
    public List<Classes> getWithDepartmentAndSession(String departmentName, int sessionId) {
        Optional<Department> department = departmentRepo.findByName(departmentName);
        Optional<Session> session = sessionRepo.findById(sessionId);
        List<Classes> clazz = classesRepo.findBySessionAndDepartment(session.get(), department.get());
        return clazz;
    }

    @Override
    public List<Classes> getWithSessionIdAndDepartmentId(int sessionId, int departmentId) {
        return classesRepo.findBySessionIdAndDepartmentId(sessionId, departmentId);
    }
}
