package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.service.SessionService;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Data
@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepo;

    @Override
    public String createSession(String name, BindingResult result) {
        Session session = new Session();
        session.setName(name);
        try {
            sessionRepo.save(session);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("name", "name", "Tên đã tồn tại");
            return "admin/session/add-session";
        }
        return "redirect:/admin/session-add";
    }

    @Override
    public List<Session> getAllSessionName() {
        return sessionRepo.findAll();
    }
}
