package com.thai.doan.service;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dto.request.SessionUpdatingRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface SessionService {
    Session createSession(String name);
    List<Session> getAllSession();
    Session updateWithId(String id, SessionUpdatingRequest sessionUpdatingReq);
    Session getWithName(String name);
    void delete(String id);
}
