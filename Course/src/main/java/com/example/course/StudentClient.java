package com.example.course;

import com.example.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service")
public interface StudentClient {
    @GetMapping("/students/{student_id}")
    Student getStudentById(@PathVariable("student_id") int studentId);
}
