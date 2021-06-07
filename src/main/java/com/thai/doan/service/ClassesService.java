package com.thai.doan.service;

import com.thai.doan.dao.model.Classes;

import java.util.List;

public interface ClassesService {
    List<Classes> getWithDepartmentAndSession(String departmentName, int sessionId);
    List<Classes> getWithSessionIdAndDepartmentId(int sessionId, int departmentId);
}
