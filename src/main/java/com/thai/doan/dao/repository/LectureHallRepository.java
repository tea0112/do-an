package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.LectureHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LectureHallRepository extends JpaRepository<LectureHall, Integer>, JpaSpecificationExecutor<LectureHall> {

}