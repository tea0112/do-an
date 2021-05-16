package com.thai.doan.dao.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentScheduleRelationFK implements Serializable {
    private int studentId;
    private int scheduleId;
}
