package com.thai.doan.controller;

import com.thai.doan.dao.model.Classroom;
import com.thai.doan.dto.request.ClassroomAddingRequest;
import com.thai.doan.dto.request.ClassroomUpdatingRequest;
import com.thai.doan.service.impl.ClassroomService;
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
public class ClassroomController {
  private final ClassroomService classroomSrv;

  // restful api
  @PostMapping("/api/admin/classrooms")
  public ResponseEntity<Classroom> add(@Valid @RequestBody ClassroomAddingRequest classroomAddingReq) {
    return new ResponseEntity<>(classroomSrv.add(classroomAddingReq), HttpStatus.OK);
  }

  @PatchMapping("/api/admin/classrooms/{id}")
  public ResponseEntity<Classroom> update(@Valid @RequestBody ClassroomUpdatingRequest classroomUpdatingReq,
                                          @PathVariable Integer id) {
    return new ResponseEntity<>(classroomSrv.update(classroomUpdatingReq, id), HttpStatus.OK);
  }

  @DeleteMapping("/api/admin/classrooms/{id}")
  public ResponseEntity<Classroom> delete(@PathVariable Integer id) {
    return new ResponseEntity<>(classroomSrv.delete(id), HttpStatus.OK);
  }

  @GetMapping("/api/admin/classrooms")
  public ResponseEntity<List<Classroom>> getWithLectureHall(@RequestParam Integer lectureHallId) {
    return new ResponseEntity<>(classroomSrv.getWithLectureHall(lectureHallId), HttpStatus.OK);
  }
}
