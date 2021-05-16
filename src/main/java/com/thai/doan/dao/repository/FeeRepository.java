package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FeeRepository extends JpaRepository<Fee, Integer>, JpaSpecificationExecutor<Fee> {

}