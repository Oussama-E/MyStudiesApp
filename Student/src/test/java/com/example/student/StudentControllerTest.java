package com.example.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest {
    private MockMvc mockMvc;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
    @DisplayName("Should return the matching student")
    public void testGetStudentById() throws Exception {
        when(studentService.getStudentById(student1.getId())).thenReturn(student1);

        mockMvc.perform(get("/api/student/{id}", student1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.firstname").value(student1.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(student1.getLastname()));
    }

    @Test
    @DisplayName("Should return not found for invalid student ID")
    public void testGetStudentByIdWithAWrongID() throws Exception {
        when(studentService.getStudentById(-1)).thenReturn(null);

        mockMvc.perform(get("/api/student/{id}", student1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return the set of all students")
    public void testGetStudents() throws Exception {
        when(studentService.getStudents()).thenReturn(students);

        mockMvc.perform(get("/api/student/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Should return not found if students list is empty or null")
    public void testGetStudentsWithAWrongSet() throws Exception {
        when(studentService.getStudents()).thenReturn(null);

        mockMvc.perform(get("/api/student/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return created status if student is created successfully")
    public void testCreateStudent() throws Exception {
        Student newStudent = new Student();
        newStudent.setLastname("Nyustu");
        newStudent.setFirstname("Dent");
        newStudent.setBirthdate(LocalDate.parse("2003-09-20"));
        newStudent.setGrade(2);

        when(studentService.createStudent(any(Student.class))).thenReturn(true);

        mockMvc.perform(post("/api/student").contentType(MediaType.APPLICATION_JSON).content("{\"firstname\": \"Dent\", \"lastname\": \"Nyustu\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return conflict if student creation fails due to existing student")
    public void testCreateStudentWithConflict() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setLastname("Abc");
        student.setFirstname("def");

        when(studentService.createStudent(any(Student.class))).thenReturn(false);

        mockMvc.perform(post("/api/student").contentType(MediaType.APPLICATION_JSON).content("{\"firstname\": \"def\", \"lastname\": \"abc\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Should update an existing student")
    public void testUpdateStudent() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(2);
        updatedStudent.setLastname("Doux");
        updatedStudent.setFirstname("Hi");
        updatedStudent.setBirthdate(LocalDate.parse("2004-04-04"));
        updatedStudent.setGrade(3);

        when(studentService.updateStudent(eq(2), any(Student.class))).thenReturn(true);

        mockMvc.perform(put("/api/student/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))  // Sérialiser l'objet Student en JSON
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return not found if student update fails due to non-existent student")
    public void testUpdateStudentNotFound() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(7);
        updatedStudent.setLastname("Doe");
        updatedStudent.setFirstname("Jane");
        updatedStudent.setBirthdate(LocalDate.parse("2000-01-01"));
        updatedStudent.setGrade(2);

        when(studentService.updateStudent(eq(7), any(Student.class))).thenReturn(false);

        mockMvc.perform(put("/api/student/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return no content if student deletion is successful")
    public void testDeleteStudent() throws Exception {
        when(studentService.deleteStudent(student2.getId())).thenReturn(true);

        mockMvc.perform(delete("/api/student/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return not found if student deletion fails due to non-existent student")
    public void testDeleteStudentNotFound() throws Exception {
        when(studentService.deleteStudent(7)).thenReturn(false);

        mockMvc.perform(delete("/api/student/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
