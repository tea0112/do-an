package com.thai.doan.service.impl;

import com.thai.doan.dao.model.*;
import com.thai.doan.dao.repository.*;
import com.thai.doan.dto.request.NewScheduleRequest;
import com.thai.doan.security.CustomUserDetails;
import com.thai.doan.service.ScheduleService;
import com.thai.doan.util.VNCharacterUtils;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepo;
    private final StudentRepository studentRepo;
    private final SemesterRepository semesterRepo;
    private final SubjectRepository subjectRepo;
    private final DepartmentRepository departmentRepo;
    private final LecturerRepository lecturerRepo;
    private final SessionRepository sessionRepo;
    private final ClassesRepository classesRepo;

    @Override
    public List<Schedule> getSchedule(int subjectType) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();
        User user = customUserDetails.getUser();
        Student std = user.getStudent();
        List<Schedule> schedule;
        if (semesterRepo.getMaxTermOfStudent(std) > 1) {
            return schedule = scheduleRepo.getCurrentSchedules(std, 1, subjectType);
        }
        else
            return schedule = scheduleRepo.getCurrentSchedules(std, 0, subjectType);
    }

    @Override
    public ModelAndView createNewSchedule(NewScheduleRequest newSchlReq, BindingResult result) {
        ModelAndView addSchedule = new ModelAndView("admin/schedule/add-schedule");
        boolean hasInvalidField = false;
        Optional<Department> department = departmentRepo.findByName(newSchlReq.getDepartment());
        Optional<Subject> subject = subjectRepo.findById(newSchlReq.getSubject());
        Optional<Lecturer> lecturer = lecturerRepo.findByIdAndDepartment(newSchlReq.getLecturer(), department.get());
        Optional<Classes> studentsClass = classesRepo.findById(newSchlReq.getClassId());
        Optional<Semester> semester = semesterRepo.findById(newSchlReq.getSemester());
        if (!department.isPresent()) {
            result.rejectValue("department", "department", "Khoa không tồn tại");
            hasInvalidField = true;
        } else if (!subject.isPresent()) {
            result.rejectValue("subject", "subject", "Môn không tồn tại");
            hasInvalidField = true;
        } else if (!lecturer.isPresent()) {
            result.rejectValue("lecturer", "lecturer", "Giảng Viên không tồn tại");
            hasInvalidField = true;
        } else if (!studentsClass.isPresent()) {
            result.rejectValue("classId", "classId", "Lớp không tồn tại");
            hasInvalidField = true;
        } else if (!semester.isPresent()) {
            result.rejectValue("semester", "semester", "Khoá không tồn tại");
            hasInvalidField = true;
        } else if (newSchlReq.getStartDay() == null) {
            result.rejectValue("startDay", "startDay", "Ngày bắt đầu không được trống");
            hasInvalidField = true;
        } else if (newSchlReq.getEndDay() == null) {
            result.rejectValue("endDay", "endDay", "Ngày kết thúc không được trống");
            hasInvalidField = true;
        }
        if (hasInvalidField) {
            addSchedule.addObject("allDepartment", departmentRepo.findAll());
            addSchedule.addObject("message", "error");
            return addSchedule;
        }
        Schedule schedule = Schedule.builder()
            .startPeriod(newSchlReq.getStartPeriod())
            .endPeriod(newSchlReq.getEndPeriod())
            .weekDay(newSchlReq.getWeek())
            .startDay(newSchlReq.getStartDay())
            .endDay(newSchlReq.getEndDay())
            .periodType(newSchlReq.getPeriodType())
            .semester(semester.get())
            .lecturer(lecturer.get())
            .subject(subject.get())
            .classes(studentsClass.get())
            .build();
        try {
            scheduleRepo.save(schedule);
        } catch (Exception e) {
            addSchedule.addObject("allDepartment", departmentRepo.findAll());
            addSchedule.addObject("message", "error");
            return addSchedule;
        }
        addSchedule.addObject("message", "success");
        addSchedule.addObject("allDepartment", departmentRepo.findAll());
        return addSchedule;
    }
}
