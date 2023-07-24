package com.thai.doan.exception;

import lombok.Data;

@Data
public class ErrorCode {
  public static final String SAVE_ERROR = "có lỗi trong quá trình lưu";
  public static final String STUDENT_CLASS_RELATION_NOT_FOUND = "Không tồn tại quan hệ sinh viên - lớp";
  public static final String LECTURE_HALL_NOT_FOUND = "Không tồn tại giảng đường";
  public static final String CLASSROOM_NOT_FOUND = "Không tồn tại phòng học";
  public static final String SEMESTER_NOT_FOUND = "Không tồn tại học kỳ";
  public static final String SESSION_NOT_FOUND = "Không tồn tại niên khoá";
  public static final String CLASS_NOT_FOUND = "Không tồn tại lớp học";
  public static final String SUBJECT_NOT_FOUND = "Không tồn tại môn";
  public static final String STUDENT_NOT_FOUND = "Không tồn tại sinh viên";
  public static final String STUDY_NOT_FOUND = "Không tồn tại học tập";
  public static final String ARGUMENT_NOT_FOUND = "Tham Số Không Tồn Tại";
  public static final String USER_NOT_FOUND = "Không tìm thấy người dùng";
}
