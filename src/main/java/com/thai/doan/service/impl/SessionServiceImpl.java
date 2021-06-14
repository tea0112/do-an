package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.dto.model.SessionCreation;
import com.thai.doan.dto.request.SessionUpdatingRequest;
import com.thai.doan.service.SessionService;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Data
@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepo;

    @Override
    public ModelAndView createSession(String name, BindingResult result) {
        Session session = new Session();
        session.setName(name);
        try {
            sessionRepo.save(session);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("name", "name", "Tên đã tồn tại");
            ModelAndView mv = new ModelAndView("admin/session/add-session", result.getModel());
            mv.addObject("sessionCreation", new SessionCreation());
            mv.addObject("message", "error");
            return mv;
        }
        ModelAndView redirect = new ModelAndView("admin/session/add-session");
        redirect.addObject("sessionCreation", new SessionCreation());
        redirect.addObject("message", "success");
        return redirect;
    }

    @Override
    public List<Session> getAllSession() {
        return sessionRepo.findAll();
    }

    @Override
    public void updateWithId(String id, SessionUpdatingRequest sessionUpdatingReq) {
        try {
            Session session = sessionRepo.findById(Integer.parseInt(id)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            session.setName(sessionUpdatingReq.getName());
            sessionRepo.save(session);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public Session getWithName(String name) {
        return sessionRepo.findByName(name).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
        );
    }
}
