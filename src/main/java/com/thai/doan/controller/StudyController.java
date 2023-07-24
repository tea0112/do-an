package com.thai.doan.controller;

import com.thai.doan.dao.model.Study;
import com.thai.doan.dto.request.StudyAddingRequest;
import com.thai.doan.dto.request.StudyUpdateRequest;
import com.thai.doan.service.impl.StudyService;
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
@RequestMapping("/api")
public class StudyController {
  private final StudyService studyService;

  @PostMapping("/admin/studies")
  public ResponseEntity<Study> add(@Valid @RequestBody StudyAddingRequest studyAddingReq) {
    return new ResponseEntity<>(studyService.add(studyAddingReq), HttpStatus.OK);
  }

  @PatchMapping("/admin/studies/{studyId}")
  public ResponseEntity<Study> update(@Valid @RequestBody StudyUpdateRequest studyUpdateReq,
                                      @PathVariable Integer studyId) {
    return new ResponseEntity<>(studyService.update(studyId, studyUpdateReq), HttpStatus.OK);
  }

  @DeleteMapping("/admin/studies/{studyId}")
  public ResponseEntity<?> delete(@PathVariable Integer studyId) {
    studyService.delete(studyId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/studies/filter")
  public ResponseEntity<List<Study>> filter(
      @RequestParam(required = false) Float minGrade,
      @RequestParam(required = false) Float maxGrade,
      @RequestParam(required = false) Boolean gradeType,
      @RequestParam(required = false) Integer subjectId,
      @RequestParam(required = false) Integer semesterId,
      @RequestParam(required = false) Integer studentId
  ) {
    return new ResponseEntity<>(studyService.filter(
        minGrade,
        maxGrade,
        gradeType,
        subjectId,
        semesterId,
        studentId), HttpStatus.OK);
  }
}
