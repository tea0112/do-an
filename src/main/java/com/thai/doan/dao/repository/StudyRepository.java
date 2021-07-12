package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Study;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.annotation.Nullable;
import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Integer>, JpaSpecificationExecutor<Study> {
    @NotNull
    List<Study> findAll(Specification<Study> spec);
}