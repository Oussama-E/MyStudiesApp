package com.example.course;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "student-service")
public interface StudentClient {

}
