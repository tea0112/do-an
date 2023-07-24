package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Classroom;
import com.thai.doan.dao.model.LectureHall;
import com.thai.doan.dao.repository.ClassroomRepository;
import com.thai.doan.dao.repository.LectureHallRepository;
import com.thai.doan.dto.request.ClassroomAddingRequest;
import com.thai.doan.dto.request.ClassroomUpdatingRequest;
import com.thai.doan.exception.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Data
public class ClassroomService {
  private final LectureHallRepository lectureHallRepo;
  private final ClassroomRepository classroomRepo;

  public Classroom add(ClassroomAddingRequest classroomAddingReq) {
    try {
      LectureHall lectureHall = lectureHallRepo.findById(classroomAddingReq.getLectureHallId()).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.LECTURE_HALL_NOT_FOUND)
      );
      Classroom classroom = Classroom.builder()
          .lectureHall(lectureHall)
          .name(classroomAddingReq.getName())
          .build();
      return classroomRepo.save(classroom);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
    }
  }

  public Classroom update(ClassroomUpdatingRequest classroomUpdatingReq, Integer id) {
    try {
      LectureHall lectureHall = lectureHallRepo.findById(classroomUpdatingReq.getLectureHallId()).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.LECTURE_HALL_NOT_FOUND)
      );
      Classroom classroom = classroomRepo.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.CLASSROOM_NOT_FOUND)
      );
      classroom.setName(classroomUpdatingReq.getName());
      classroom.setLectureHall(lectureHall);
      return classroomRepo.save(classroom);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
    }
  }

  public Classroom delete(Integer id) {
    try {
      Classroom classroom = classroomRepo.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.CLASSROOM_NOT_FOUND)
      );
      classroomRepo.delete(classroom);
      return classroom;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
    }
  }

  public List<Classroom> getWithLectureHall(Integer lectureHallId) {
    try {
      LectureHall lectureHall = lectureHallRepo.findById(lectureHallId).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.LECTURE_HALL_NOT_FOUND)
      );
      return classroomRepo.findByLectureHall(lectureHall);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
    }
  }
}
