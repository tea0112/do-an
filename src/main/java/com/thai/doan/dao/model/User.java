package com.thai.doan.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username", nullable = false, unique = true)
  @Size(min = 1, max = 500)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "is_admin", nullable = false)
  private Boolean admin;

  @JsonIgnore
  @OneToOne(mappedBy = "user")
  private Student student;
}
