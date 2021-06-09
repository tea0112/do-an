package com.thai.doan.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "fees")
public class Fee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount_of_money", nullable = false)
    private Integer amountOfMoney;

    @Column(name = "fee_type", nullable = false)
    private Integer feeType;

    @Column(name = "start_day", nullable = false)
    private LocalDate startDay;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @JsonIgnore
    @OneToMany(mappedBy = "fee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Retraining> retrainings;

    @JsonIgnore
    @OneToMany(mappedBy = "fee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Semester> semesters;
}
