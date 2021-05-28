package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.StudentClassRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentClassRelationRepository extends JpaRepository<StudentClassRelation, Integer>, JpaSpecificationExecutor<StudentClassRelation> {

}