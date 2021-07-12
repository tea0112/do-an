package com.thai.doan.controller;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dto.model.SessionCreation;
import com.thai.doan.dto.request.SessionUpdatingRequest;
import com.thai.doan.service.SessionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@SecurityRequirement(name = "bearerAuth")
@RestController
@Data
public class SessionController {
    private final SessionService sessionSv;

    //curd
    @PostMapping("/api/admin/sessions")
    public @ResponseBody
    ResponseEntity<Session> createSession(@RequestBody SessionCreation sessionCreation) {
        return new ResponseEntity<>(sessionSv.createSession(sessionCreation.getName()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/admin/sessions", method = RequestMethod.GET)
    public List<Session> getAllSessions() {
        return sessionSv.getAllSession();
    }

    @RequestMapping(value = "/api/admin/sessions", method = RequestMethod.GET, params = "name")
    public Session getWithName(@RequestParam String name) {
        return sessionSv.getWithName(name);
    }

    @PatchMapping("/api/admin/sessions/{id}")
    public ResponseEntity<Session> updateWithId(@PathVariable String id,
                                                @Valid @RequestBody SessionUpdatingRequest sessionUpdatingReq) {
        return new ResponseEntity<>(sessionSv.updateWithId(id, sessionUpdatingReq), HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/sessions/{id}")
    public ResponseEntity<?> updateWithId(@PathVariable String id) {
        sessionSv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
