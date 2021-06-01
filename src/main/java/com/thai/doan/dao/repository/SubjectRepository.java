package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer>, JpaSpecificationExecutor<Subject> {

    Optional<Subject> findBySubjectTypeAndName(int subjectType, String name);
}