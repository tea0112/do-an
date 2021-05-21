package com.thai.doan.dao.repository;

import com.thai.doan.dao.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer>, JpaSpecificationExecutor<Session> {
    Optional<Session> findByName(String name);
}