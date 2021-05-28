package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Schedule;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.Lecturer;
import com.thai.doan.dao.model.StudentClassRelation;
import com.thai.doan.dao.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
            ") " +
            "and sd.periodType = :periodType " +
            "and sd.classes = ( " +
                "select distinct c " +
                "from Student std " +
                "join StudentClassRelation scr on std = scr.studentId " +
                "join Classes c on scr.classId = c " +
                "where c.classType = :classType " +
                "and std = :currentStudent" +
            ")"
    )
    List<Schedule> getCurrentSchedules(Student currentStudent, int classType, int periodType);
}