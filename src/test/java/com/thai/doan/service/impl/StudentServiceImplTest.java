package com.thai.doan.service.impl;

import com.thai.doan.dao.model.Student;
import com.thai.doan.dao.model.User;
import com.thai.doan.dao.repository.StudentRepository;
import com.thai.doan.dao.repository.UserRepository;
import com.thai.doan.dto.request.PasswordChangeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StudentServiceImplTest {
    @MockBean
    private StudentRepository studentRepo;
    @MockBean
    private UserRepository userRepo;
    @MockBean
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

    }
}