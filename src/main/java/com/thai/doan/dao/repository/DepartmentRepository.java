package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
    Optional<Department> findByName(String department);

    Optional<Department> findByClasses(Classes clazz);

    Integer countByIsGeneralTrue();
}