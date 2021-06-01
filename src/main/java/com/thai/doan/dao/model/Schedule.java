package com.thai.doan.dao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "schedules")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_day", nullable = false)
    private LocalDate startDay;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_day", nullable = false)
    private LocalDate endDay;

    @Column(name = "week_day", nullable = false)
    private int weekDay;

    @Column(name = "period_type")
    private int periodType;

    @Column(name = "start_period", nullable = false)
    private Integer startPeriod;

    @Column(name = "end_period", nullable = false)
    private Integer endPeriod;

    @OneToMany(mappedBy = "scheduleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentScheduleRelation> studentScheduleRelations;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;
}
