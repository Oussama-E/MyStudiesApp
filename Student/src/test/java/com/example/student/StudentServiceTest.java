package com.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private Student student2;
    private Set<Student> students;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1);
        student.setFirstname("John");
        student.setLastname("Doe");
        students = Set.of(student, student2);

        student2 = new Student();
        student2.setId(2);
        student2.setFirstname("Ay");
        student2.setLastname("Do");

    }

    @Test
    @DisplayName("Should return the matching student")
    public void testGetStudentById(){
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1);

        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
    }

    @Test
    @DisplayName("Should return not found")
    public void testGetStudentByIdWithAWrongID(){
        when(studentRepository.findById(-1)).thenReturn(Optional.empty());

        Student result = studentService.getStudentById(-1);

        assertNull(result);
    }

    @Test
    @DisplayName("Should return the set of all students")
    public void testGetStudents(){
        when(studentRepository.findAll()).thenReturn(new ArrayList<>(students));
    }

}
