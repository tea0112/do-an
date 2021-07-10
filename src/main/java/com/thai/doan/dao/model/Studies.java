package com.thai.doan.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "studies")
public class Studies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grade", nullable = false)
    private Float grade;

    @Column(name = "subject_id", nullable = false)
    private Integer subjectId;

    @Column(name = "semester_id", nullable = false)
    private Integer semesterId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "grade_type", nullable = false)
    private Boolean gradeType;

}
