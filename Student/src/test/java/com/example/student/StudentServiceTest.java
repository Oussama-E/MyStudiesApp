package com.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

        student2 = new Student();
        student2.setId(2);
        student2.setFirstname("Ay");
        student2.setLastname("Do");

        students = Set.of(student, student2);

    }

    @Test
    @DisplayName("Should return the student with the matching ID")
    public void testGetStudentById(){
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1);

        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
    }

    @Test
    @DisplayName("Should return null if student ID is not found")
    public void testGetStudentByIdWithAWrongID(){
        when(studentRepository.findById(-1)).thenReturn(Optional.empty());

        Student result = studentService.getStudentById(-1);

        assertNull(result);
    }

    @Test
    @DisplayName("Should return the set of all students")
    public void testGetStudents(){
        when(studentRepository.findAll()).thenReturn(new ArrayList<>(students));

        Set<Student> result = studentService.getStudents();

        assertEquals(students, result);
    }

    @Test
    @DisplayName("Should return an empty set if repository returns null")
    public void testGetStudentsWithWrongSet(){
        when(studentRepository.findAll()).thenReturn(null);

        Set<Student> result = studentService.getStudents();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result should be an empty set when repository returns null");
    }

    @Test
    @DisplayName("Should return true if the student is created successfully")
    public void testCreateStudent(){
        Student newStudent = new Student();
        newStudent.setId(4);
        newStudent.setLastname("Nyustu");
        newStudent.setFirstname("Dent");
        newStudent.setBirthdate(LocalDate.parse("2003-09-20"));
        newStudent.setGrade(2);

        when(studentRepository.existsById(4)).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);

        Boolean result = studentService.createStudent(newStudent);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false if student creation fails due to existing student ID")
    public void testCreateStudentWithConflict(){
        Student existingStudent = new Student();
        existingStudent.setId(1);
        existingStudent.setLastname("Existing");
        existingStudent.setFirstname("Student");

        when(studentRepository.existsById(1)).thenReturn(true);

        Boolean result = studentService.createStudent(existingStudent);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should update an existing student successfully")
    public void testUpdateStudent(){
        Student updatedStudent = new Student();
        updatedStudent.setId(2);
        updatedStudent.setLastname("Doux");
        updatedStudent.setFirstname("Hi");
        updatedStudent.setBirthdate(LocalDate.parse("2004-04-04"));
        updatedStudent.setGrade(3);

        when(studentRepository.findById(2)).thenReturn(Optional.of(student2));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        Boolean result = studentService.updateStudent(student2.getId(), updatedStudent);

        assertTrue(result);

        verify(studentRepository).save(student2);
    }

    @Test
    @DisplayName("Should return false if the student to update is not found")
    public void testUpdateStudentNotFound(){
        Student updatedStudent = new Student();
        updatedStudent.setId(7);
        updatedStudent.setLastname("Do");
        updatedStudent.setFirstname("Ay");
        updatedStudent.setBirthdate(LocalDate.parse("2001-01-01"));
        updatedStudent.setGrade(2);

        when(studentRepository.findById(7)).thenReturn(Optional.empty());

        Boolean result = studentService.updateStudent(7, updatedStudent);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true if the student is deleted successfully")
    public void testDeleteStudent(){
        when(studentRepository.existsById(2)).thenReturn(true);

        Boolean result = studentService.deleteStudent(student2.getId());

        assertTrue(result);

        verify(studentRepository).deleteById(student2.getId());
    }

    @Test
    @DisplayName("Should return false if the student to delete is not found")
    public void testDeleteStudentNotFound(){
        when(studentRepository.existsById(7)).thenReturn(false);

        Boolean result = studentService.deleteStudent(7);

        assertFalse(result);
    }


}
