package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Semester;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.Study;
import com.thai.doan.dao.model.Subject;
import com.thai.doan.dao.repository.SemesterRepository;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.dao.repository.StudyRepository;
import com.thai.doan.dao.repository.SubjectRepository;
import com.thai.doan.dto.request.StudyAddingRequest;
import com.thai.doan.exception.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Data
@Service
public class StudyService {
    private final StudyRepository studyRepo;
    private final SubjectRepository subjectRepo;
    private final SemesterRepository semesterRepo;
    private final StudentRepository studentRepo;

    public Study add(StudyAddingRequest studyAddingReq) {
        Subject subject = subjectRepo.findById(studyAddingReq.getSubjectId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SUBJECT_NOT_FOUND));
        Semester semester = semesterRepo.findById(studyAddingReq.getSemesterId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SEMESTER_NOT_FOUND));
        Student student = studentRepo.findById(studyAddingReq.getStudentId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.STUDENT_NOT_FOUND));

        Study study = Study.builder()
            .grade(studyAddingReq.getGrade())
            .subject(subject)
            .semester(semester)
            .student(student)
            .gradeType(studyAddingReq.getGradeType())
            .build();

        try {
            return studyRepo.save(study);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
    }
}
