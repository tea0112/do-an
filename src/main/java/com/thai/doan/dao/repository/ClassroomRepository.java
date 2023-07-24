package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Classroom;
import com.thai.doan.dao.model.LectureHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer>, JpaSpecificationExecutor<Classroom> {
  List<Classroom> findByLectureHall(LectureHall id);
}