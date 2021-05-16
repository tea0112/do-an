package com.thai.doan.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "subjects")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "grade")
    private Float grade;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;

    @ManyToOne
    @JoinColumn(name = "retraining_id")
    private Retraining retraining;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
