package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Fee;
import com.thai.doan.dao.model.Semester;
import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.FeeRepository;
import com.thai.doan.dao.repository.SemesterRepository;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.dto.request.SemesterAddingRequest;
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
    private final FeeRepository feeRepo;

    @Override
    public List<Semester> getWithSession(int sessionId) {
        Optional<Session> session = sessionRepo.findById(sessionId);
        return semesterRepo.findBySession(session.get());
    }

    @Override
    public void add(SemesterAddingRequest semesterAddingReq) {
        try {
            Session session = sessionRepo.findById(semesterAddingReq.getSessionId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Fee fee = feeRepo.findById(1).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Semester semester = Semester.builder()
                .termNumber(semesterAddingReq.getTermNumber())
                .startDay(semesterAddingReq.getStartDay())
                .endDay(semesterAddingReq.getEndDay())
                .session(session)
                .fee(fee)
                .build();
            semesterRepo.save(semester);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }
}
