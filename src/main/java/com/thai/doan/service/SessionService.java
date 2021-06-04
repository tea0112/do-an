package com.thai.doan.service;

import com.thai.doan.dao.model.Session;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SessionService {
    String createSession(String name, BindingResult result);
    List<Session> getAllSession();
}
