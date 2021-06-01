package com.thai.doan.service.impl;

import com.thai.doan.dao.model.*;
import com.thai.doan.dao.repository.*;
import com.thai.doan.dto.request.NewScheduleRequest;
import com.thai.doan.security.CustomUserDetails;
import com.thai.doan.service.ScheduleService;
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
        Optional<Subject> subject = subjectRepo.findBySubjectTypeAndName(
            newSchlReq.getSubjectType(), newSchlReq.getSubject());
        Optional<Department> department = departmentRepo.findByName(newSchlReq.getDepartment());
        Optional<Lecturer> lecturer = lecturerRepo.findByNameAndDepartment(
            newSchlReq.getLecturer(), department.get());
        Optional<Session> session = sessionRepo.findByName(newSchlReq.getSession());
        int newestTerm = semesterRepo.getNewestTermOfSession(session.get());
        Optional<Classes> studentsClass = classesRepo.findBySession(session.get());
        Optional<Semester> semester = semesterRepo.findBySessionAndTermNumber(session.get(), newestTerm);

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
        scheduleRepo.save(schedule);
        return null;
    }
}
