package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Retraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RetrainingRepository extends JpaRepository<Retraining, Integer>, JpaSpecificationExecutor<Retraining> {

}