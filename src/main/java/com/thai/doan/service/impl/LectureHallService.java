package com.thai.doan.service.impl;

import com.thai.doan.dao.model.LectureHall;
import com.thai.doan.dao.repository.LectureHallRepository;
import com.thai.doan.dto.request.LectureHallAddingRequest;
import com.thai.doan.dto.request.LectureHallUpdatingRequest;
import com.thai.doan.exception.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Data
public class LectureHallService {
  private final LectureHallRepository lectureHallRepo;

  public LectureHall add(LectureHallAddingRequest lectureHallAddingReq) {
    try {
      LectureHall lectureHall = LectureHall.builder()
          .name(lectureHallAddingReq.getName().trim())
          .address(lectureHallAddingReq.getAddress())
          .build();
      return lectureHallRepo.save(lectureHall);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
    }
  }

  public LectureHall update(LectureHallUpdatingRequest lectureHallUpdatingReq, Integer id) {
    try {
      LectureHall lectureHall = lectureHallRepo.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.LECTURE_HALL_NOT_FOUND)
      );
      lectureHall.setName(lectureHallUpdatingReq.getName());
      lectureHall.setAddress(lectureHallUpdatingReq.getAddress());
      return lectureHallRepo.save(lectureHall);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
    }
  }

  public LectureHall delete(Integer id) {
    try {
      LectureHall lectureHall = lectureHallRepo.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.LECTURE_HALL_NOT_FOUND)
      );
      lectureHallRepo.delete(lectureHall);
      return lectureHall;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.LECTURE_HALL_NOT_FOUND);
    }
  }
}
