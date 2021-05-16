package com.thai.doan.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "retrainings")
public class Retraining implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "retraining_type", nullable = false)
    private Integer retrainingType;

    @Column(name = "start_day", nullable = false)
    private LocalDate startDay;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;

    @OneToMany(mappedBy = "retraining", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(mappedBy = "retraining", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subject> subjects;
}
