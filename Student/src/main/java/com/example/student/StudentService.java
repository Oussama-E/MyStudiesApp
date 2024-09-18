package com.example.student;

import org.springframework.stereotype.Service;

import java.util.Collection;
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
        Collection<Student> students = studentRepository.findAll();
        return students != null ? new HashSet<>(students) : new HashSet<>();
    }

    public Boolean createStudent(Student s) {
        if (studentRepository.existsById(s.getId())) return false;
        studentRepository.save(s);
        return true;
    }

    public Boolean updateStudent(int id, Student updatedStudent) {
        Student currentStudent = studentRepository.findById(id).orElse(null);
        if (currentStudent == null) {
            return false;
        }
        currentStudent.setLastname(updatedStudent.getLastname());
        currentStudent.setFirstname(updatedStudent.getFirstname());
        currentStudent.setBirthdate(updatedStudent.getBirthdate());
        currentStudent.setGrade(updatedStudent.getGrade());
        studentRepository.save(currentStudent);
        return true;
    }

    public Boolean deleteStudent(int id) {
        if (!studentRepository.existsById(id))
            return false;
        studentRepository.deleteById(id);
        return true;
    }
}
