package com.thai.doan.dao.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentClassRelationFK implements Serializable {
    private int studentId;
    private int classId;
}
