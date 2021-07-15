package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.User;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.dao.repository.UserRepository;
import com.thai.doan.dto.request.PasswordChangeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testUpdatePassword() {
        User user = User.builder()
            .password("123")
            .admin(false)
            .username("abc")
            .id(1)
            .build();
        Student student = Student.builder()
            .id(1)
            .birth(LocalDate.of(2000, 1, 12))
            .firstName("a")
            .lastName("b")
            .gender(false)
            .phoneNumber("0987893345")
            .place("abc")
            .user(user)
            .session(new Session())
            .build();
        user.setStudent(student);
        PasswordChangeRequest passwordChangeReq = new PasswordChangeRequest(1, "abc");
        Mockito.when(studentRepo.findById(passwordChangeReq.getStudentId())).thenReturn(Optional.ofNullable(student));
        studentService.updatePassword(passwordChangeReq);
        Mockito.verify(userRepo, Mockito.times(1)).save(ArgumentMatchers.any(User.class));

    }
}