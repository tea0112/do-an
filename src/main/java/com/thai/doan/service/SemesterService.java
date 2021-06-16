package com.thai.doan.service;

import com.thai.doan.dao.model.Semester;
import com.thai.doan.dto.request.SemesterAddingRequest;
import com.thai.doan.dto.request.SemesterUpdatingRequest;

import java.util.List;

public interface SemesterService {
    List<Semester> getWithSession(int sessionId);
    void add(SemesterAddingRequest semesterAddingReq);
    void updateWithId(SemesterUpdatingRequest semesterUpdatingReq, Integer id);
    void delete(Integer id);
}
