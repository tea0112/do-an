package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.User;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.dao.repository.UserRepository;
import com.thai.doan.dto.request.NewStudentRequest;
import com.thai.doan.service.SessionService;
import com.thai.doan.service.StudentService;
import com.thai.doan.util.VNCharacterUtils;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
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
            .email(stdReq.getEmail())
            .password(pwdEcd.encode(stdReq.getPassword()))
            .student(student)
            .build();
        student.setUser(user);
        try {
            studentRepo.save(student);
        } catch (DataIntegrityViolationException e) {
            addStudent.addObject("sessionNames", sessionSv.getAllSession());
            result.rejectValue("email", "email", "Email đã tồn tại");
            return addStudent;
        }
        return new ModelAndView("redirect:/admin/add-student");
    }
}
