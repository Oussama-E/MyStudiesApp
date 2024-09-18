package com.example.student;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Set<Student> getStudents(){
        return new HashSet<>(studentRepository.findAll());
    }

    public Boolean createStudent(Student s) {
        if (studentRepository.existsById(s.getId())) return false;
        studentRepository.save(s);
        return true;
    }

    public Boolean updateStudent(Student updatedStudent) {
        Student currentStudent = studentRepository.findById(updatedStudent.getId()).orElseThrow();
        currentStudent.setLastname(updatedStudent.getLastname());
        currentStudent.setFirstname(updatedStudent.getFirstname());
        currentStudent.setBirthdate(updatedStudent.getBirthdate());
        currentStudent.setGrade(updatedStudent.getGrade());
        studentRepository.save(currentStudent);
        return true;
    }
}
