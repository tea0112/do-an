package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudyRepository extends JpaRepository<Study, Integer>, JpaSpecificationExecutor<Study> {

}