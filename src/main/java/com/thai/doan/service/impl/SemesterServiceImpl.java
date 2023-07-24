package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Semester;
import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.SemesterRepository;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.dto.request.SemesterAddingRequest;
import com.thai.doan.dto.request.SemesterUpdatingRequest;
import com.thai.doan.service.SemesterService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class SemesterServiceImpl implements SemesterService {
  private final SessionRepository sessionRepo;
  private final SemesterRepository semesterRepo;

  @Override
  public List<Semester> getWithSession(int sessionId) {
    Optional<Session> session = sessionRepo.findById(sessionId);
    return semesterRepo.findBySession(session.get());
  }

  @Override
  public void add(SemesterAddingRequest semesterAddingReq) {
    try {
      if (semesterAddingReq.getStartDay().isAfter(semesterAddingReq.getEndDay())) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }
      Session session = sessionRepo.findById(semesterAddingReq.getSessionId()).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
      );
      Semester semester = Semester.builder()
          .termNumber(semesterAddingReq.getTermNumber())
          .startDay(semesterAddingReq.getStartDay())
          .endDay(semesterAddingReq.getEndDay())
          .session(session)
          .build();
      semesterRepo.save(semester);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }

  @Override
  public void updateWithId(SemesterUpdatingRequest semesterUpdatingReq, Integer id) {
    try {
      Session session = sessionRepo.findById(semesterUpdatingReq.getSessionId()).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
      );
      Semester semester = Semester.builder()
          .id(id)
          .termNumber(semesterUpdatingReq.getTermNumber())
          .startDay(semesterUpdatingReq.getStartDay())
          .endDay(semesterUpdatingReq.getEndDay())
          .session(session)
          .build();
      semesterRepo.save(semester);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }

  @Override
  public void delete(Integer id) {
    try {
      Semester semester = semesterRepo.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
      );
      semesterRepo.delete(semester);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }
}
