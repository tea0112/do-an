package com.thai.doan.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "birth", nullable = false)
    private Date birth;

    @Column(name = "place")
    private String place;

    @ManyToOne
    @JoinColumn(name = "retraining_id")
    private Retraining retraining;

    @OneToMany(mappedBy = "studentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentScheduleRelation> studentScheduleRelations;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
