package com.thai.doan.controller;

import com.thai.doan.dao.model.Schedule;
import com.thai.doan.dao.model.Subject;
import com.thai.doan.dto.request.NewScheduleRequest;
import com.thai.doan.dto.request.ScheduleUpdatingRequest;
import com.thai.doan.service.DepartmentService;
import com.thai.doan.service.LecturerService;
import com.thai.doan.service.ScheduleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@Data
public class ScheduleController {
    private final ScheduleService scheduleSv;
    private final DepartmentService departmentSv;
    private final LecturerService lecturerSv;

    // View

    @GetMapping("/thoi-khoa-bieu/ly-thuyet")
    public ModelAndView getTheorySchedule() {
        ModelAndView mav = new ModelAndView("client/schedule/theory");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        mav.addObject("currentTime", dtf.format(now));
        return mav;
    }

    @GetMapping("/thoi-khoa-bieu/thuc-hanh")
    public ModelAndView getPracticeSchedule() {
        ModelAndView mav = new ModelAndView("client/schedule/practice");
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

    @GetMapping("/admin/thoi-khoa-bieu/sua")
    public ModelAndView getAdminEditSchedule() {
        return new ModelAndView("admin/schedule/edit-schedule");
    }

    @RequestMapping(value = "/admin/thoi-khoa-bieu/sua", method = RequestMethod.GET, params = "scheduleId")
    public ModelAndView getAdminIdEditSchedule(@RequestParam("scheduleId") int scheduleId) {
        return new ModelAndView("admin/schedule/id-edit-schedule");
    }

    @GetMapping("/admin/thoi-khoa-bieu/xoa")
    public ModelAndView getAdminDeleteSchedule() {
        return new ModelAndView("admin/schedule/delete-schedule");
    }


    // restful api

    @PostMapping("/api/admin/schedules")
    public ResponseEntity<Schedule> createNewSchedule(@RequestBody NewScheduleRequest newSchlReq) {
        return new ResponseEntity<>(scheduleSv.createNewSchedule(newSchlReq), HttpStatus.CREATED);
    }

    @GetMapping("/thoi-khoa-bieu/ly-thuyet/theory-schedule")
    public @ResponseBody
    ResponseEntity<List<Schedule>> getRESTTheorySchedule() {
        return new ResponseEntity<>(scheduleSv.getSchedule(Subject.SUBJECT_TYPE.THEORY.ordinal()), HttpStatus.OK);
    }

    @GetMapping("/thoi-khoa-bieu/thuc-hanh/practice-schedule")
    public @ResponseBody
    ResponseEntity<List<Schedule>> getRESTPracticeSchedule() {
        return new ResponseEntity<>(scheduleSv.getSchedule(Subject.SUBJECT_TYPE.PRACTICE.ordinal()), HttpStatus.OK);
    }

    @GetMapping("/api/schedules")
    public List<Schedule> getWithClassIdAndSemesterId(@RequestParam int classId, @RequestParam int semesterId) {
        return scheduleSv.getWithClassIdAndSemesterId(classId, semesterId);
    }

    ;

    @GetMapping("/api/admin/schedules/{id}")
    public ResponseEntity<?> getSchedule(@PathVariable String id) {
        return new ResponseEntity<>(scheduleSv.getOneSchedule(id), HttpStatus.OK);
    }

    @PatchMapping("/api/admin/schedules/{id}")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody @Valid ScheduleUpdatingRequest scheduleUpdatingReq,
                                                   @PathVariable String id) {
        return new ResponseEntity<>(scheduleSv.updateSchedule(scheduleUpdatingReq, id), HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/schedules/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable String id) {
        scheduleSv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/schedules/lecturer")
    public ResponseEntity<List<Schedule>> retrieveLecturerLike(
        @RequestParam(required = false) String name
    ) {
        List<Schedule> schedules = scheduleSv.lecturerNameLike(name);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/api/schedules/search")
    public ResponseEntity<List<Schedule>> retrieveSchedules(
        @RequestParam(required = false) Integer weekDay,
        @RequestParam(required = false) Integer periodType,
        @RequestParam(required = false) Integer semesterId) {
        List<Schedule> schedules = scheduleSv.retrieveSchedules(weekDay, periodType, semesterId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
}
