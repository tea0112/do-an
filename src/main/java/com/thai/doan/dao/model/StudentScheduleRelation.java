package com.thai.doan.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@IdClass(StudentScheduleRelationFK.class)
@Table(name = "student_schedule_relation")
public class StudentScheduleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule scheduleId;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentId;

}
