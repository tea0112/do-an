package com.thai.doan.controller;

import com.thai.doan.dao.model.Schedule;
import com.thai.doan.dao.model.Subject;
import com.thai.doan.dto.request.NewScheduleRequest;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.service.DepartmentService;
import com.thai.doan.service.LecturerService;
import com.thai.doan.service.ScheduleService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Data
public class ScheduleController {
    private final ScheduleService scheduleSv;
    private final DepartmentService departmentSv;
    private final LecturerService lecturerSv;

    // View

    @GetMapping("/thoi-khoa-bieu/ly-thuyet")
    public ModelAndView getTheorySchedule() {
        ModelAndView mav = new ModelAndView("schedule/theory");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        mav.addObject("currentTime", dtf.format(now));
        return mav;
    }

    @GetMapping("/thoi-khoa-bieu/thuc-hanh")
    public ModelAndView getPracticeSchedule() {
        ModelAndView mav = new ModelAndView("schedule/practice");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        mav.addObject("currentTime", dtf.format(now));
        return mav;
    }

    // admin view
    @GetMapping("/admin/thoi-khoa-bieu/them")
    public ModelAndView getAdminAddSchedule() {
        ModelAndView mav = new ModelAndView("admin/schedule/add-schedule");
        mav.addObject("newScheduleRequest", new NewScheduleRequest());
        mav.addObject("allDepartment", departmentSv.getAllDepartments());
        return mav;
    }

    // curd
    @PostMapping("/admin/thoi-khoa-bieu/them")
    public ModelAndView createNewSchedule(
        @Valid NewScheduleRequest newSchlReq, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("admin/schedule/add-schedule", result.getModel());
            mav.addObject("allDepartment", departmentSv.getAllDepartments());
            mav.addObject("newScheduleRequest", new NewScheduleRequest());
            mav.addObject("message", "error");
            return mav;
        }
        return scheduleSv.createNewSchedule(newSchlReq, result);
    }


    // restful api
    @GetMapping("/thoi-khoa-bieu/ly-thuyet/theory-schedule")
    public @ResponseBody ResponseEntity<List<Schedule>> getRESTTheorySchedule() {
        return new ResponseEntity<>(scheduleSv.getSchedule(Subject.SUBJECT_TYPE.THEORY.ordinal()), HttpStatus.OK);
    }

    @GetMapping("/thoi-khoa-bieu/thuc-hanh/practice-schedule")
    public @ResponseBody ResponseEntity<List<Schedule>> getRESTPracticeSchedule() {
        return new ResponseEntity<>(scheduleSv.getSchedule(Subject.SUBJECT_TYPE.PRACTICE.ordinal()), HttpStatus.OK);
    }

}
