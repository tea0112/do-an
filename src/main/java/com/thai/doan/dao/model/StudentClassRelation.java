package com.thai.doan.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@IdClass(StudentClassRelationFK.class)
@Table(name = "student_class_relation")
public class StudentClassRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student studentId;

    @Id
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classes classId;

}
