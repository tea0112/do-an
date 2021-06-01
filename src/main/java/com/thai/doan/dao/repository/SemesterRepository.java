package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Semester;
import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, Integer>, JpaSpecificationExecutor<Semester> {
    @Query("select max(smtr.termNumber) from Semester smtr " +
        "join Session ss on smtr.session = ss " +
        "where ss in ( " +
        "    select stdnt.session from Student stdnt " +
        "    where stdnt = :student " +
        ")")
    int getMaxTermOfStudent(Student student);

    @Query("select max(smt.termNumber) from Semester smt join Session ss on smt.session = ss where ss = :session")
    int getNewestTermOfSession(Session session);

    Optional<Semester> findBySessionAndTermNumber(Session session, int newestTermNumber);
}