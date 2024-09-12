package com.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StudentControllerTest {
    private MockMvc mockMvc;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    private Student student1;
    private Student student2;
    private Set<Student> students;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        student1 = new Student();
        student1.setId(1);
        student1.setFirstname("John");
        student1.setLastname("Doe");

        student2 = new Student();
        student2.setId(2);
        student2.setFirstname("Ay");
        student2.setLastname("Do");

        students = Set.of(student1, student2);
    }

    @Test
    public void testGetStudentById() throws Exception {
        when(studentService.getStudentById(student1.getId())).thenReturn(student1);

        mockMvc.perform(get("/api/student/{id}", student1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.firstname").value(student1.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(student1.getLastname()));
    }

    @Test
    public void testGetStudentByIdWithAWrongID() throws Exception {
        when(studentService.getStudentById(-1)).thenReturn(null);

        mockMvc.perform(get("/api/student/{id}", student1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetStudents() throws Exception {
        when(studentService.getStudents()).thenReturn(students);

        mockMvc.perform(get("/api/student/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetStudentsWithAWrongSet() throws Exception {
        when(studentService.getStudents()).thenReturn(null);

        mockMvc.perform(get("/api/student/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateStudent() throws Exception {
        Student newStudent = new Student();
        newStudent.setLastname("Nyustu");
        newStudent.setFirstname("Dent");
        newStudent.setBirthdate(LocalDate.parse("2003-09-20"));
        newStudent.setGrade(2);

        when(studentService.createStudent(any(Student.class))).thenReturn(newStudent);

        mockMvc.perform(post("/api/student").contentType(MediaType.APPLICATION_JSON).content("{\"firstname\": \"Dent\", \"lastname\": \"Nyustu\"}"))
                .andExpect(status().isCreated());
    }
}
