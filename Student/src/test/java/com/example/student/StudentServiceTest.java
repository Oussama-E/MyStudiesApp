package com.example.student;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("Should return the matching student")
    public void testGetStudentById(){
        Student student = new Student();
        student.setId(1);
        student.setFirstname("John");
        student.setLastname("Doe");

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

}
