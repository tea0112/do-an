package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer>, JpaSpecificationExecutor<Classroom> {

}