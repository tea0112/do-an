package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.StudentScheduleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentScheduleRelationRepository extends JpaRepository<StudentScheduleRelation, Integer>, JpaSpecificationExecutor<StudentScheduleRelation> {

}