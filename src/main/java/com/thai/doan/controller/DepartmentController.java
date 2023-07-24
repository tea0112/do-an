package com.thai.doan.controller;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dto.request.DepartmentAddingRequest;
import com.thai.doan.dto.request.DepartmentUpdatingRequest;
import com.thai.doan.service.DepartmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Data
@RestController
public class DepartmentController {
  private final DepartmentService departmentService;

  // RESTFul api
  @GetMapping("/api/departments")
  public List<Department> getAllDepartments() {
    return departmentService.getAllDepartments();
  }

  @PostMapping("/api/admin/departments")
  public ResponseEntity<?> add(@Valid @RequestBody DepartmentAddingRequest departmentAddingReq) {
    departmentService.add(departmentAddingReq);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/api/admin/departments/{id}",
      method = RequestMethod.PATCH)
  public ResponseEntity<?> update(@PathVariable Integer id,
                                  @Valid @RequestBody DepartmentUpdatingRequest departmentUpdatingReq) {
    departmentService.update(id, departmentUpdatingReq);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/api/admin/departments/{id}",
      method = RequestMethod.DELETE)
  public ResponseEntity<?> update(@PathVariable Integer id) {
    departmentService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
