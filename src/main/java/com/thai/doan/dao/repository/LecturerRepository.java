package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer>, JpaSpecificationExecutor<Lecturer> {
    Optional<Lecturer> findByNameAndDepartment(String lecturer, Department department);
    List<Lecturer> findAllByDepartment(Department department);
}