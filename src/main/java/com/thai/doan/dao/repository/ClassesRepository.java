package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Department;
import com.thai.doan.dao.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClassesRepository extends JpaRepository<Classes, Integer>, JpaSpecificationExecutor<Classes> {
    List<Classes> findBySession(Session session);
    List<Classes> findBySessionAndDepartment(Session session, Department department);
    @Query("select cls from Classes cls " +
        "where cls.session.id = :sessionId and cls.department.id = :departmentId")
    List<Classes> findBySessionIdAndDepartmentId(int sessionId, int departmentId);
    List<Classes> findByDepartmentAndSession(Department department, Session session);
    List<Classes> findBySessionAndDepartment_IsGeneralTrue(Session session);
}