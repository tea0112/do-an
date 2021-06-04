package com.thai.doan.controller;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dto.model.SessionCreation;
import com.thai.doan.service.SessionService;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@Data
public class SessionController {
    private final SessionService sessionSv;
    //view
    @GetMapping("/admin/session-add")
    public ModelAndView addSession() {
        ModelAndView mv = new ModelAndView("admin/session/add-session");
        mv.addObject("sessionCreation", new SessionCreation());
        return mv;
    }

    //curd
    @PostMapping("/admin/session")
    public String createSession(@Valid SessionCreation sessionCreation,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "admin/session/add-session";
        }
        return sessionSv.createSession(sessionCreation.getName(), result);
    }

    @GetMapping("/admin/session")
    public List<Session> getAllSession() {
        return sessionSv.getAllSession();
    }
}
