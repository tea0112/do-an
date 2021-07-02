package com.thai.doan.controller;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dto.model.SessionCreation;
import com.thai.doan.dto.request.SessionUpdatingRequest;
import com.thai.doan.service.SessionService;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/admin/nien-khoa/sua")
    public ModelAndView editSession() {
        ModelAndView mv = new ModelAndView("admin/session/edit-session");
        mv.addObject("sessionCreation", new SessionCreation());
        mv.addObject("message", "");
        return mv;
    }

    @GetMapping("/admin/nien-khoa/xoa")
    public ModelAndView deleteSession() {
        return new ModelAndView("admin/session/delete-session");
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

    @RequestMapping(value = "/api/admin/sessions", method = RequestMethod.GET, params = "name")
    public Session getWithName(@RequestParam String name) {
        return sessionSv.getWithName(name);
    }

    @GetMapping("/api/admin/sessions")
    public List<Session> getAllSessions() {
        return sessionSv.getAllSession();
    }

    @PatchMapping("/admin/sessions/{id}")
    public ResponseEntity<?> updateWithId(@PathVariable String id,
                                          @Valid @RequestBody SessionUpdatingRequest sessionUpdatingReq) {
        sessionSv.updateWithId(id, sessionUpdatingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/sessions/{id}")
    public ResponseEntity<?> updateWithId(@PathVariable String id) {
        sessionSv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
