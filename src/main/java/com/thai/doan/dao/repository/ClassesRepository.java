package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ClassesRepository extends JpaRepository<Classes, Integer>, JpaSpecificationExecutor<Classes> {
    Optional<Classes> findBySession(Session session);
    List<Classes> findBySessionAndDepartment(Session session, Department department);
}