package com.example.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id){
        Student std = studentService.getStudentById(id);
        if (std==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(std, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Student>> getStudents(){
        Set<Student> students = studentService.getStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
