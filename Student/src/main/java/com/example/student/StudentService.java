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

    /**
     * Search for a student by ID number.
     *
     * @param id the ID of the student
     * @return the student with the given ID, or null if no student is found
     */
    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    /**
     * Collect all students.
     *
     * @return a set of all students. Returns an empty set if no students are found
     */
    public Set<Student> getStudents(){
        Collection<Student> students = studentRepository.findAll();
        return students != null ? new HashSet<>(students) : new HashSet<>();
    }

    /**
     * Creates a new student.
     *
     * @param s the student to create
     * @return true if the student was successfully created, false if a student with the same ID already exists
     */
    public Boolean createStudent(Student s) {
        if (studentRepository.existsById(s.getId())) return false;
        studentRepository.save(s);
        return true;
    }

    /**
     * Updates an existing student.
     *
     * @param id the ID of the student to update
     * @param updatedStudent the student object with updated information
     * @return true if the student was successfully updated, false if no student with the given ID was found
     */
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

    /**
     * Deletes a student by ID.
     *
     * @param id the ID of the student to delete
     * @return true if the student was successfully deleted, false if no student with the given ID was found
     */
    public Boolean deleteStudent(int id) {
        if (!studentRepository.existsById(id))
            return false;
        studentRepository.deleteById(id);
        return true;
    }
}
