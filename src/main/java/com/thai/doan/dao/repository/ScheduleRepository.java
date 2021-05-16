package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

}