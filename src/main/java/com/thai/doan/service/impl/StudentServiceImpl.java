package com.thai.doan.service.impl;

import com.thai.doan.dao.model.*;
import com.thai.doan.dao.repository.*;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.dto.request.StudentUpdatingRequest;
import com.thai.doan.exception.ErrorCode;
import com.thai.doan.security.CustomUserDetails;
import com.thai.doan.service.SessionService;
import com.thai.doan.service.StudentService;
import com.thai.doan.util.VNCharacterUtils;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepo;
    private final SessionRepository sessionRepo;
    private final ClassesRepository classesRepo;
    private final StudentClassRelationRepository studentClassRelationRepo;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder pwdEcd;
    private final SessionService sessionSv;

    @Override
    public Student createNewStudent(NewStudentRequest stdReq) {
        Session session = sessionRepo.findById(stdReq.getSessionId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SESSION_NOT_FOUND));
        Classes clazz = classesRepo.findById(stdReq.getClassId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.CLASS_NOT_FOUND));
        Student student = Student.builder()
            .lastName(stdReq.getLastName().trim())
            .firstName(stdReq.getFirstName().trim())
            .birth(stdReq.getBirth())
            .place(stdReq.getPlace().trim())
            .phoneNumber(stdReq.getPhoneNumber().trim())
            .session(session)
            .gender(stdReq.getGender())
            .build();
        User user = User.builder()
            .admin(false)
            .username(stdReq.getUsername().trim())
            .password(pwdEcd.encode(stdReq.getPassword()))
            .student(student)
            .build();
        student.setUser(user);
        try {
            Student saved = studentRepo.save(student);
            StudentClassRelation studentClassRelation = StudentClassRelation.builder()
                .classId(clazz)
                .studentId(saved)
                .build();
            studentClassRelationRepo.save(studentClassRelation);
            return saved;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorCode.SAVE_ERROR);
        }
    }

    @Override
    public Student updateWithId(StudentUpdatingRequest studentUpdatingReq, String id) {
        try {
            Student student = studentRepo.findById(Integer.parseInt(id)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            student.setGender(studentUpdatingReq.getGender());
            student.setLastName(studentUpdatingReq.getLastName());
            student.setFirstName(studentUpdatingReq.getFirstName());
            student.setBirth(studentUpdatingReq.getBirth());
            student.setPlace(studentUpdatingReq.getPlace());
            student.setPhoneNumber(studentUpdatingReq.getPhoneNumber());
            return studentRepo.save(student);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public Student getWithId(String studentId) {
        try {
            return studentRepo.findById(Integer.parseInt(studentId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public Student getAuthenticated() {
        CustomUserDetails customUserDetails =
            (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = customUserDetails.getUser();
        return currentUser.getStudent();
    }

    @Transactional
    @Override
    public void delete(Integer studentId) {
        try {
            Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            studentRepo.delete(student);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }

    @Override
    public List<Student> getWithSession(Integer sessionId) {
        try {
            Session session = sessionRepo.findById(sessionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            return studentRepo.findBySession(session);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
        }
    }
}
