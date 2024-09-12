package com.example.course;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "teacher-service")
public interface TeacherClient {

}
