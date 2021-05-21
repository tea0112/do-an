package com.thai.doan.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "students")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "place")
    private String place;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "retraining_id")
    private Retraining retraining;

    @OneToMany(mappedBy = "studentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentScheduleRelation> studentScheduleRelations;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @NotNull
    private Session session;
}
