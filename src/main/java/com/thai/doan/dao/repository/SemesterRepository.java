package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SemesterRepository extends JpaRepository<Semester, Integer>, JpaSpecificationExecutor<Semester> {

}