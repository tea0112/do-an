package com.thai.doan.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "schedules")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_day", nullable = false)
    private LocalDate startDay;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subject> subjects;

    @OneToMany(mappedBy = "scheduleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentScheduleRelation> studentScheduleRelations;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;


}
