package com.thai.doan.controller;

import com.thai.doan.dao.model.LectureHall;
import com.thai.doan.dao.repository.LectureHallRepository;
import com.thai.doan.dto.request.LectureHallAddingRequest;
import com.thai.doan.dto.request.LectureHallUpdatingRequest;
import com.thai.doan.service.impl.LectureHallService;
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
public class LectureHallController {
  // service
  private final LectureHallService lectureHallSrv;
  // repo
  private final LectureHallRepository lectureHallRepo;

  // restful api
  @GetMapping("/api/lecturerHalls")
  public List<LectureHall> getAll() {
    return lectureHallRepo.findAll();
  }

  @PostMapping("/api/admin/lectureHalls")
  public ResponseEntity<LectureHall> add(@RequestBody LectureHallAddingRequest lectureHallAddingReq) {
    return new ResponseEntity<>(lectureHallSrv.add(lectureHallAddingReq), HttpStatus.OK);
  }

  @PatchMapping("/api/admin/lectureHalls/{id}")
  public ResponseEntity<LectureHall> update(@Valid @RequestBody LectureHallUpdatingRequest lectureHallUpdatingReq,
                                            @PathVariable Integer id) {
    return new ResponseEntity<>(lectureHallSrv.update(lectureHallUpdatingReq, id), HttpStatus.OK);
  }

  @DeleteMapping("/api/admin/lectureHalls/{id}")
  public ResponseEntity<LectureHall> delete(@PathVariable Integer id) {
    return new ResponseEntity<>(lectureHallSrv.delete(id), HttpStatus.OK);
  }
}
