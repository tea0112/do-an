package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Lecturer;
import com.thai.doan.dao.repository.DepartmentRepository;
import com.thai.doan.dao.repository.LecturerRepository;
import com.thai.doan.dto.request.LecturerAddingRequest;
import com.thai.doan.dto.request.LecturerUpdatingRequest;
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

  @Override
  public void add(LecturerAddingRequest lecturerAddingReq) {
    try {
      Department department = departmentRepo.findById(Integer.parseInt(lecturerAddingReq.getDepartmentId()))
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
      Lecturer lecturer = Lecturer.builder()
          .name(lecturerAddingReq.getName())
          .department(department)
          .build();
      lecturerRepo.save(lecturer);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }

  @Override
  public Lecturer getOne(String id) {
    try {
      return lecturerRepo.findById(Integer.parseInt(id))
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
  }

  @Override
  public void updateWithId(LecturerUpdatingRequest lecturerUpdatingReq, Integer id) {
    try {
      Lecturer lecturer = lecturerRepo.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
      Department department = departmentRepo.findById(lecturerUpdatingReq.getDepartmentId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
      lecturer.setName(lecturerUpdatingReq.getName());
      lecturer.setDepartment(department);
      lecturerRepo.save(lecturer);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }

  @Override
  public void delete(Integer id) {
    Lecturer lecturer = lecturerRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
    lecturerRepo.delete(lecturer);
  }
}
