package com.thai.doan.exception;

import lombok.Data;

@Data
public class ErrorCode {
    public static final String CLASS_NOT_FOUND = "CLASS_NOT_FOUND";
    public static final String STUDENT_CLASS_RELATION_NOT_FOUND = "STUDENT_CLASS_RELATION_NOT_FOUND";

    public static final String SAVE_ERROR = "có lỗi trong quá trình lưu";
    public static final String LECTURE_HALL_NOT_FOUND = "không tìm thấy giảng đường";
    public static final String CLASSROOM_NOT_FOUND = "không tìm thấy phòng học";
    public static final String SEMESTER_NOT_FOUND = "không tìm thấy niên khoá";
}
