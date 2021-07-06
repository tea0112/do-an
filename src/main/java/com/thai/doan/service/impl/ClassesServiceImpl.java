package com.thai.doan.service.impl;

import com.thai.doan.dao.model.*;
import com.thai.doan.dao.repository.*;
import com.thai.doan.dto.request.ClassAddingRequest;
import com.thai.doan.dto.request.ClassUpdatingRequest;
import com.thai.doan.service.ClassesService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class ClassesServiceImpl implements ClassesService {
    private final ClassesRepository classesRepo;
    private final DepartmentRepository departmentRepo;
    private final SessionRepository sessionRepo;
    private final StudentRepository studentRepo;
    private final StudentClassRelationRepository studentClassRelationRepo;

    @Override
    public List<Classes> getWithDepartmentAndSession(String departmentName, int sessionId) {
        Optional<Department> department = departmentRepo.findByName(departmentName);
        Optional<Session> session = sessionRepo.findById(sessionId);
        return classesRepo.findBySessionAndDepartment(session.get(), department.get());
    }

    @Override
    public List<Classes> getWithSessionIdAndDepartmentId(int sessionId, int departmentId) {
        return classesRepo.findBySessionIdAndDepartmentId(sessionId, departmentId);
    }

    @Override
    public void add(ClassAddingRequest classAddingReq) {
        try {
            Session session = sessionRepo.findById(classAddingReq.getSessionId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Department department = departmentRepo.findById(classAddingReq.getDepartmentId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Classes clazz = Classes.builder()
                .name(classAddingReq.getName())
                .session(session)
                .department(department)
                .build();
            classesRepo.save(clazz);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public void updateWithId(int id, ClassUpdatingRequest classUpdatingReq) {
        try {
            Session session = sessionRepo.findById(classUpdatingReq.getSessionId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Department department = departmentRepo.findById(classUpdatingReq.getDepartmentId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Classes clazz = classesRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            clazz.setName(classUpdatingReq.getName());
            clazz.setSession(session);
            clazz.setDepartment(department);
            classesRepo.save(clazz);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public void deleteWithId(int id) {
        try {
            Classes clazz = classesRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            classesRepo.delete(clazz);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public List<Classes> getWithClassTypeAndDepartmentAndSession(Integer classType, Integer departmentId,
                                                                 Integer sessionId) {
        try {
            Department department = departmentRepo.findById(departmentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Session session = sessionRepo.findById(sessionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            return classesRepo.findByDepartmentAndSession(department, session);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public List<Classes> getGeneralWithSession(Integer sessionId) {
        try {
            Session session = sessionRepo.findById(sessionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            return classesRepo.findBySessionAndDepartment_IsGeneralTrue(session);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public List<Classes> getSpecializedClassWithSessionId(Integer sessionId) {
        try {
            Session session = sessionRepo.findById(sessionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            return classesRepo.findBySession(session);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public List<Classes> getWithSession(Integer sessionId) {
        try {
            Session session = sessionRepo.findById(sessionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            return classesRepo.findBySession(session);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.toString());
        }
    }
}
