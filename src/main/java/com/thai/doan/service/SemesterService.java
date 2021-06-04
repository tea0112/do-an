package com.thai.doan.service;

import com.thai.doan.dao.model.Semester;

import java.util.List;

public interface SemesterService {
    List<Semester> getWithSession(int sessionId);
}
