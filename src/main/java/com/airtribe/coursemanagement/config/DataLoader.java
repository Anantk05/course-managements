package com.airtribe.coursemanagement.config;

import com.airtribe.coursemanagement.model.Course;
import com.airtribe.coursemanagement.model.Student;
import com.airtribe.coursemanagement.model.Enrollment;
import com.airtribe.coursemanagement.repository.CourseRepository;
import com.airtribe.coursemanagement.repository.StudentRepository;
import com.airtribe.coursemanagement.repository.EnrollmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CourseRepository courseRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        return args -> {
            Course c1 = new Course("Java Spring Boot", "Intro to Spring Boot and REST APIs", LocalDate.now().plusDays(7), LocalDate.now().plusMonths(1));
            Course c2 = new Course("Database Design", "Relational design and Postgres basics", LocalDate.now().plusDays(10), LocalDate.now().plusMonths(2));

            courseRepository.save(c1);
            courseRepository.save(c2);

            Student s1 = new Student("Alice", "Johnson", "alice@example.com");
            Student s2 = new Student("Bob", "Smith", "bob@example.com");

            studentRepository.save(s1);
            studentRepository.save(s2);

            Enrollment e1 = new Enrollment(s1, c1, LocalDate.now(), "ENROLLED");
            Enrollment e2 = new Enrollment(s2, c2, LocalDate.now(), "ENROLLED");

            enrollmentRepository.save(e1);
            enrollmentRepository.save(e2);
        };
    }
}

