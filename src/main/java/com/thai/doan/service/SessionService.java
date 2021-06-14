package com.thai.doan.service;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dto.request.SessionUpdatingRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface SessionService {
    ModelAndView createSession(String name, BindingResult result);
    List<Session> getAllSession();
    void updateWithId(String id, SessionUpdatingRequest sessionUpdatingReq);
    Session getWithName(String name);
}
