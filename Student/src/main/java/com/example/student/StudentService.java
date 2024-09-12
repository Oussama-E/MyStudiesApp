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

    public Student createStudent(Student s) {
        return studentRepository.save(s);
    }
}
