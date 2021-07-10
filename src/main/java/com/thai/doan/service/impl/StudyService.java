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
import com.thai.doan.dto.request.StudyUpdateRequest;
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
        if (studyAddingReq.getGrade() < 0 && studyAddingReq.getGrade() > 10) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }

        Study study = Study.builder()
            .grade(studyAddingReq.getGrade())
            .subject(subject)
            .semester(semester)
            .student(student)
            .gradeType(subject.getSubjectType() != 0)
            .build();

        try {
            return studyRepo.save(study);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
    }

    public Study update(Integer studyId, StudyUpdateRequest studyUpdateReq) {
        Subject subject = subjectRepo.findById(studyUpdateReq.getSubjectId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SUBJECT_NOT_FOUND));
        Study study = studyRepo.findById(studyId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.STUDY_NOT_FOUND));
        if (studyUpdateReq.getGrade() < 0 && studyUpdateReq.getGrade() > 10) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
        study.setGrade(studyUpdateReq.getGrade());
        study.setSubject(subject);
        study.setGradeType(subject.getSubjectType() != 0);
        try {
            return studyRepo.save(study);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
    }
}
