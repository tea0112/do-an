package com.thai.doan.dao.repository;

import com.sun.istack.Nullable;
import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Schedule;
import com.thai.doan.dao.model.Semester;
import com.thai.doan.dao.model.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {
  @Query(
      "select sd " +
          "from Schedule sd " +
          "join Semester st on sd.semester = st " +
          "join Subject sj on sd.subject = sj " +
          "join Lecturer l on sd.lecturer = l " +
          "where st.termNumber = (" +
          "select distinct max(st.termNumber) " +
          "from Semester st " +
          "join Session ss on st.session = ss" +
          " where ss.id = :currentSession" +
          ") " +
          "and sj.subjectType = :subjectType " +
          "and sd.classes = ( " +
          "select distinct c " +
          "from Student std " +
          "join StudentClassRelation scr on std = scr.studentId " +
          "join Classes c on scr.classId = c " +
          "where std = :currentStudent " +
          "and c.id in (" +
          "select cl.id " +
          "from Department d " +
          "join Classes cl on d.id = cl.department.id " +
          "where d.isGeneral = :isGeneral" +
          ")" +
          ")"
  )
  List<Schedule> getCurrentSchedules(Student currentStudent, int subjectType, int currentSession, boolean isGeneral);

  @Query("select schdl from Schedule schdl where schdl.classes.id = :classId and schdl.semester.id = :semesterId")
  List<Schedule> getWithClassIdAndSemesterId(int classId, int semesterId);

  List<Schedule> findByClassesAndSemester(Classes clazz, Semester semester);

  Optional<Schedule> findById(int id);

  List<Schedule> findAll(@Nullable Specification<Schedule> spec);

}