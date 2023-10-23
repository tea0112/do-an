package com.thai.doan.controller;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dto.request.ClassAddingRequest;
import com.thai.doan.dto.request.ClassUpdatingRequest;
import com.thai.doan.service.ClassesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@Data
public class ClassController {
  private final ClassesService classSv;

  @RequestMapping(
      value = "/api/classes",
      method = RequestMethod.GET,
      params = {"sessionId", "departmentId"})
  public List<Classes> getWithDepartmentAndSession(
      @RequestParam(name = "departmentId") int departmentId, @RequestParam(name = "sessionId") int sessionId) {
    return classSv.getWithDepartmentAndSession(departmentId, sessionId);
  }

  @PostMapping("/api/admin/classes")
  public ResponseEntity<?> add(@RequestBody @Valid ClassAddingRequest classAddingReq) {
    classSv.add(classAddingReq);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/api/admin/classes")
  public ResponseEntity<?> updateWithId(@RequestBody @Valid ClassUpdatingRequest classUpdatingReq,
                                        @RequestParam int classId) {
    classSv.updateWithId(classId, classUpdatingReq);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/api/admin/classes/{classId}")
  public ResponseEntity<?> deleteWithId(@PathVariable int classId) {
    classSv.deleteWithId(classId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/api/classes", method = RequestMethod.GET, params = "sessionId")
  public List<Classes> getGeneralWithSession(@RequestParam Integer sessionId) {
    List<Classes> cls =  classSv.getGeneralWithSession(sessionId);
    return cls;
  }

  @RequestMapping(value = "/api/specializedClass", method = RequestMethod.GET, params = "sessionId")
  public List<Classes> getSpecializedClassWithSessionId(@RequestParam Integer sessionId) {
    return classSv.getSpecializedClassWithSessionId(sessionId);
  }

  @RequestMapping(value = "/api/admin/classes", method = RequestMethod.GET, params = "sessionId")
  public List<Classes> getWithSession(@RequestParam Integer sessionId) {
    return classSv.getWithSession(sessionId);
  }
}
