package com.example.gradesys;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//TODO add tests
@SpringBootApplication
public class StudentGradeSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentGradeSystemApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
