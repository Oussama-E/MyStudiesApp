package com.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.when;

public class StudentControllerTest {
    private MockMvc mockMvc;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        student1 = new Student();
        student1.setId(1);
        student1.setFirstname("John");
        student1.setLastname("Doe");
    }

    @Test
    public void testGetStudentById() throws Exception {
        when(studentService.getStudentById(student1.getId())).thenReturn(student1);

    }
}
