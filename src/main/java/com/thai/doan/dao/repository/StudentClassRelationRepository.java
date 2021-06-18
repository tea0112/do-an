package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.StudentClassRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StudentClassRelationRepository extends JpaRepository<StudentClassRelation, Integer>, JpaSpecificationExecutor<StudentClassRelation> {
    List<StudentClassRelation> findByClassId(Classes clazz);
    List<StudentClassRelation> findByStudentId(Student student);
}