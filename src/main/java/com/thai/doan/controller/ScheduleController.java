package com.thai.doan.controller;

import com.thai.doan.service.ScheduleService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Data
public class ScheduleController {
    private final ScheduleService scheduleSv;

    @GetMapping("/thoi-khoa-bieu/ly-thuyet")
    public ModelAndView getTheorySchedule() {
        scheduleSv.getSchedule();
        ModelAndView mav = new ModelAndView("schedule/theory");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        mav.addObject("currentTime", dtf.format(now));
        return mav;
    }
}
