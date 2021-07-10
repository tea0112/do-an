package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Studies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudiesRepository extends JpaRepository<Studies, Integer>, JpaSpecificationExecutor<Studies> {

}