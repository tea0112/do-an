package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.StudentClassRelation;
import com.thai.doan.dao.repository.ClassesRepository;
import com.thai.doan.dao.repository.StudentClassRelationRepository;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.dao.repository.UserRepository;
import com.thai.doan.exception.ErrorCode;
import com.thai.doan.service.StudentClassRelationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class StudentClassRelationServiceImpl implements StudentClassRelationService {
    private final StudentClassRelationRepository studentClassRelationRepo;
    private final StudentRepository studentRepo;
    private final ClassesRepository classesRepo;
    private final UserRepository userRepo;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Student> getWithClassId(String classId) {
        Classes clazz = classesRepo.findById(Integer.parseInt(classId)).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.CLASS_NOT_FOUND)
        );

        List<StudentClassRelation> studentClassRelations = studentClassRelationRepo.findByClassId(clazz);
        if (studentClassRelations == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.STUDENT_CLASS_RELATION_NOT_FOUND);
        }

        return studentClassRelations
            .stream()
            .map(StudentClassRelation::getStudentId)
            .collect(Collectors.toList());

    }

    @Override
    public void addStudentToClass(int studentId, int classId) {
        try {
            Classes clazz = classesRepo.findById(classId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<StudentClassRelation> q = cb.createQuery(StudentClassRelation.class);
            Root<StudentClassRelation> root = q.from(StudentClassRelation.class);
            Predicate hasMoreThanTwoRow = cb.equal(root.get("studentId"), student);
            q.where(cb.and(hasMoreThanTwoRow));
            List<StudentClassRelation> results = em.createQuery(q.select(root)).getResultList();
            if (results.size() >= 2) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sinh viên thuộc tối đa 2 lớp");
            }

            StudentClassRelation studentClassRlt = StudentClassRelation.builder()
                .studentId(student)
                .classId(clazz)
                .build();
            studentClassRelationRepo.save(studentClassRlt);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public void removeStudentFromClass(int studentId, int classId) {
        try {
            Classes clazz = classesRepo.findById(classId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            StudentClassRelation studentClassRlt = StudentClassRelation.builder()
                .studentId(student)
                .classId(clazz)
                .build();
            studentClassRelationRepo.delete(studentClassRlt);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public List<StudentClassRelation> getWithStudentId(Integer studentId) {
        try {
            Student student =
                studentRepo.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
            return studentClassRelationRepo.findByStudentId(student);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
