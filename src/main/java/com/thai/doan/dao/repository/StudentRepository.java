package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
  @Query("select max(smtr.termNumber) from Semester smtr " +
      "join Session ss on smtr.session = ss " +
      "where ss in ( " +
      "    select stdnt.session from Student stdnt " +
      "    where stdnt = :student " +
      ")")
  int getCurrentTerm(Student student);

  List<Student> findBySession(Session session);
}