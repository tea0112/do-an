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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Study study = studyRepo.findById(studyId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.STUDY_NOT_FOUND));
        if (studyUpdateReq.getGrade() < 0 && studyUpdateReq.getGrade() > 10) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
        study.setGrade(studyUpdateReq.getGrade());
        try {
            return studyRepo.save(study);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
    }

    public void delete(Integer studyId) {
        Study study = studyRepo.findById(studyId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.STUDY_NOT_FOUND));
        try {
            studyRepo.delete(study);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
    }

    public List<Study> filter(Float minGrade,
                              Float maxGrade,
                              Boolean gradeType,
                              Integer subjectId,
                              Integer semesterId,
                              Integer studentId) {
        Specification<Study> gtEqThanMinGrade = (root, query, builder) -> Optional.ofNullable(minGrade)
            .map(value -> builder.greaterThanOrEqualTo(root.<Float>get("grade"), value)).orElse(null);
        Specification<Study> ltEqThanMaxGrade = (root, query, builder) -> Optional.ofNullable(maxGrade)
            .map(value -> builder.lessThanOrEqualTo(root.<Float>get("grade"), value)).orElse(null);
        Specification<Study> eqToGradeType = (root, query, builder) -> Optional.ofNullable(gradeType)
            .map(value -> builder.equal(root.get("gradeType"), gradeType)).orElse(null);
        Specification<Study> eqToSubjectId = (root, query, builder) -> Optional.ofNullable(subjectId)
            .map(value -> builder.equal(root.get("subject").<Integer>get("id"), value)).orElse(null);
        Specification<Study> eqToSemesterId = (root, query, builder) -> Optional.ofNullable(semesterId)
            .map(value -> builder.equal(root.get("semester").<Integer>get("id"), value)).orElse(null);
        Specification<Study> eqToStudentId = (root, query, builder) -> Optional.ofNullable(studentId)
            .map(value -> builder.equal(root.get("student").<Integer>get("id"), value)).orElse(null);
        return studyRepo.findAll(
            gtEqThanMinGrade
                .and(ltEqThanMaxGrade)
                .and(eqToGradeType)
                .and(eqToSubjectId)
                .and(eqToSemesterId)
                .and(eqToStudentId));
    }
}
