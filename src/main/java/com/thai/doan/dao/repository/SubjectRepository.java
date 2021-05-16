package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubjectRepository extends JpaRepository<Subject, Integer>, JpaSpecificationExecutor<Subject> {

}