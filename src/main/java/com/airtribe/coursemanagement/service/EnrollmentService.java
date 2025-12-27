package com.airtribe.coursemanagement.service;

import com.airtribe.coursemanagement.exception.DuplicateEnrollmentException;
import com.airtribe.coursemanagement.model.Course;
import com.airtribe.coursemanagement.model.Enrollment;
import com.airtribe.coursemanagement.model.Student;
import com.airtribe.coursemanagement.repository.CourseRepository;
import com.airtribe.coursemanagement.repository.EnrollmentRepository;
import com.airtribe.coursemanagement.repository.StudentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Enrollment enroll(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NoSuchElementException("Course not found"));

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new DuplicateEnrollmentException("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment(student, course, LocalDate.now(), "ENROLLED");
        try {
            return enrollmentRepository.save(enrollment);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Failed to create enrollment: " + e.getMessage());
        }
    }

    public List<Enrollment> listAll() { return enrollmentRepository.findAll(); }

    public List<Enrollment> findByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) throw new NoSuchElementException("Student not found");
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> findByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) throw new NoSuchElementException("Course not found");
        return enrollmentRepository.findByCourseId(courseId);
    }

    public void cancel(Long enrollmentId) {
        if (!enrollmentRepository.existsById(enrollmentId)) throw new NoSuchElementException("Enrollment not found");
        enrollmentRepository.deleteById(enrollmentId);
    }
}
