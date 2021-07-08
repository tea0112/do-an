package com.thai.doan.service;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dto.request.ClassAddingRequest;
import com.thai.doan.dto.request.ClassUpdatingRequest;

import java.util.List;

public interface ClassesService {
    List<Classes> getWithDepartmentAndSession(int departmentId, int sessionId);

    void add(ClassAddingRequest classAddingReq);

    void updateWithId(int id, ClassUpdatingRequest classUpdatingReq);

    void deleteWithId(int id);

    List<Classes> getWithClassTypeAndDepartmentAndSession(Integer classType, Integer departmentId, Integer sessionId);

    List<Classes> getGeneralWithSession(Integer sessionId);

    List<Classes> getSpecializedClassWithSessionId(Integer sessionId);

    List<Classes> getWithSession(Integer sessionId);
}
