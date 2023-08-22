package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.repository.DepartmentRepository;
import com.thai.doan.dto.request.DepartmentAddingRequest;
import com.thai.doan.dto.request.DepartmentUpdatingRequest;
import com.thai.doan.service.DepartmentService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Data
public class DepartmentServiceImpl implements DepartmentService {
  private final DepartmentRepository departmentRepo;

  @Override
  public List<Department> getAllDepartments() {
    return departmentRepo.findAll();
  }

  @Override
  public void update(Integer id, DepartmentUpdatingRequest departmentUpdatingReq) {
    try {
      boolean isGeneral = departmentUpdatingReq.getIsGeneral();
      Department department = departmentRepo.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
      );
      department.setName(departmentUpdatingReq.getName());
      if (department.isGeneral() != isGeneral && isGeneral == true) {
        if (departmentRepo.countByIsGeneralTrue() >= 1) {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Exist more than one isGeneral row");
        }
      }
      department.setIsGeneral(isGeneral);
      departmentRepo.save(department);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }

  @Override
  public void add(DepartmentAddingRequest departmentAddingReq) {
    try {
      boolean isGeneral = departmentAddingReq.getIsGeneral();
      if (isGeneral) {
        if (departmentRepo.countByIsGeneralTrue() >= 1) {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Exist more than one isGeneral row");
        }
      }
      Department department = Department.builder()
          .name(departmentAddingReq.getName())
          .isGeneral(isGeneral)
          .build();
      departmentRepo.save(department);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }

  @Override
  public void delete(Integer id) {
    try {
      Department department = departmentRepo.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
      );
      departmentRepo.delete(department);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }
  }
}

