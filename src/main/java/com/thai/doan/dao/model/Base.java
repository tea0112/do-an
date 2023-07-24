package com.thai.doan.dao.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class Base {
  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "created_by")
  @CreatedBy
  private Integer createdBy;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatdAt;

  @Column(name = "updated_by")
  @LastModifiedBy
  private Integer updatedBy;
}
