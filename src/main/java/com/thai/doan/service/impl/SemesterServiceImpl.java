package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Semester;
import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.SemesterRepository;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.service.SemesterService;
import lombok.Data;
import org.springframework.stereotype.Service;

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
}
