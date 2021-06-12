package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.User;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.dao.repository.UserRepository;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.dto.request.StudentUpdatingRequest;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Data
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepo;
    private final SessionRepository sessionRepo;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder pwdEcd;
    private final SessionService sessionSv;

    @Override
    public ModelAndView createNewStudent(NewStudentRequest stdReq, BindingResult result) {
        ModelAndView addStudent = new ModelAndView("admin/student/add-student");
        Optional<Session> sessionOpl = sessionRepo.findByName(stdReq.getSessionName());
        boolean hasInvalidField = false;
        if (!sessionOpl.isPresent()) {
            result.rejectValue("sessionName", "sessionNotFound", "Niên khoá không tồn tại");
            hasInvalidField = true;
        } else if (!stdReq.getPassword().equals(stdReq.getRepassword())) {
            result.rejectValue("repassword", "repassword", "Mật khẩu nhập lại không khớp");
            hasInvalidField = true;
        } else if (!VNCharacterUtils.removeAccent(stdReq.getFirstName()).matches("^[a-zA-Z ]+$")) {
            result.rejectValue("firstName", "firstName", "Tên chỉ chứa ký tự là chữ");
            hasInvalidField = true;
        } else if (!VNCharacterUtils.removeAccent(stdReq.getLastName()).matches("^[a-zA-Z ]+$")) {
            result.rejectValue("lastName", "lastName", "Họ chỉ chứa ký tự là chữ");
            hasInvalidField = true;
        }
        if (hasInvalidField) {
            addStudent.addObject("sessionNames", sessionSv.getAllSession());
            addStudent.addObject("message", "error");
            return addStudent;
        }
        Student student = Student.builder()
            .lastName(stdReq.getLastName())
            .firstName(stdReq.getFirstName())
            .birth(stdReq.getBirth())
            .place(stdReq.getPlace())
            .phoneNumber(stdReq.getPhoneNumber())
            .session(sessionOpl.get())
            .build();
        User user = User.builder()
            .admin(false)
            .username(stdReq.getUsername())
            .password(pwdEcd.encode(stdReq.getPassword()))
            .student(student)
            .build();
        student.setUser(user);
        try {
            studentRepo.save(student);
        } catch (DataIntegrityViolationException e) {
            addStudent.addObject("sessionNames", sessionSv.getAllSession());
            result.rejectValue("username", "username", "username đã tồn tại");
            return addStudent;
        }
        ModelAndView redirect = new ModelAndView("redirect:/admin/sinh-vien/them");
        redirect.addObject("message", "success");
        return redirect;
    }

    @Override
    public void updateWithId(StudentUpdatingRequest studentUpdatingReq, String id) {
        try {
            Student student = studentRepo.findById(Integer.parseInt(id)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
            );
            student.setLastName(studentUpdatingReq.getLastName());
            student.setFirstName(studentUpdatingReq.getFirstName());
            student.setBirth(studentUpdatingReq.getBirth());
            student.setPlace(studentUpdatingReq.getPlace());
            student.setPhoneNumber(studentUpdatingReq.getPhoneNumber());
            studentRepo.save(student);
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
}
