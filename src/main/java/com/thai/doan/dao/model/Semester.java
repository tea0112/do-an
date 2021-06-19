package com.thai.doan.dao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "semesters")
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "term_number", nullable = false)
    private Integer termNumber;

    @Column(name = "start_day", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;

    @Column(name = "end_day", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDay;

    @JsonIgnore
    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}
