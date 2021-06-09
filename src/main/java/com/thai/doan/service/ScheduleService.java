package com.thai.doan.service;

import com.thai.doan.dao.model.Schedule;
import com.thai.doan.dto.request.NewScheduleRequest;
import com.thai.doan.dto.request.ScheduleUpdatingRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ScheduleService {
    List<Schedule> getSchedule(int subjectType);
    ModelAndView createNewSchedule(NewScheduleRequest newSchlReq, BindingResult result);
    List<Schedule> getWithClassIdAndSemesterId(int classId, int semesterId);
    void updateSchedule(ScheduleUpdatingRequest scheduleUpdatingReq, String id);
    Schedule getOneSchedule(String id);
}
