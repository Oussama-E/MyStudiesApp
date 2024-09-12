package com.example.student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "StudentEntity")
@Table(name = "students")
public class Student {
    @Id
    private int id;
    private String lastname;
    private String firstname;
    private LocalDate birthdate;
    private int grade;
    /*@ElementCollection
    private HashMap<Integer, Long> cursus;*/
}
