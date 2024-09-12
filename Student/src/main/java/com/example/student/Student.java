package com.example.student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashMap;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String lastname;
    private String firstname;
    private LocalDate birthdate;
    private int grade;
    /*@ElementCollection
    private HashMap<Integer, Long> cursus;*/
}
