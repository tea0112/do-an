package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Schedule;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.repository.ScheduleRepository;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.service.ScheduleService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Data
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepo;
    private final StudentRepository studentRepo;

    @Override
    public void getSchedule() {

        Student std = studentRepo.findById(2).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
        );
        int currentTerm = studentRepo.getCurrentTerm(std);
        List<Schedule> jpql = scheduleRepo.getCurrentSchedules(std, 1, 1);
        System.out.println();
    }
}
