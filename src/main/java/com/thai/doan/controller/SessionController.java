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
    @GetMapping("/admin/nien-khoa/them")
    public ModelAndView addSession() {
        ModelAndView mv = new ModelAndView("admin/session/add-session");
        mv.addObject("sessionCreation", new SessionCreation());
        mv.addObject("message", "");
        return mv;
    }

    //curd
    @PostMapping("/admin/nien-khoa/them")
    public ModelAndView createSession(@Valid SessionCreation sessionCreation,
                                BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("admin/session/add-session", result.getModel());
            mv.addObject("sessionCreation", new SessionCreation());
            mv.addObject("message", "error");
            return mv;
        }
        return sessionSv.createSession(sessionCreation.getName(), result);
    }

    @GetMapping("/admin/session")
    public List<Session> getAllSession() {
        return sessionSv.getAllSession();
    }
}
