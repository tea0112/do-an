package com.thai.doan.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studies")
public class Study implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "grade", nullable = false)
  private Float grade;

  @Column(name = "grade_type", nullable = false)
  private Boolean gradeType;

  @ManyToOne
  @JoinColumn(name = "subject_id")
  private Subject subject;

  @ManyToOne
  @JoinColumn(name = "semester_id")
  private Semester semester;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  public enum GRADE_TYPE {
    THEORY,
    PRACTICE
  }
}
