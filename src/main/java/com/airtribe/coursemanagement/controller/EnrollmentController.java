package com.airtribe.coursemanagement.controller;

import com.airtribe.coursemanagement.dto.EnrollmentDTO;
import com.airtribe.coursemanagement.dto.EnrollmentRequest;
import com.airtribe.coursemanagement.model.Enrollment;
import com.airtribe.coursemanagement.service.EnrollmentService;
import com.airtribe.coursemanagement.mapper.EnrollmentMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentController(EnrollmentService enrollmentService, EnrollmentMapper enrollmentMapper) {
        this.enrollmentService = enrollmentService;
        this.enrollmentMapper = enrollmentMapper;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enroll(@Valid @RequestBody EnrollmentRequest request) {
        Enrollment e = enrollmentService.enroll(request.getStudentId(), request.getCourseId());
        EnrollmentDTO dto = enrollmentMapper.toDTO(e);
        return ResponseEntity.created(URI.create("/api/enrollments/" + dto.getId())).body(dto);
    }

    @GetMapping
    public List<EnrollmentDTO> list() {
        return enrollmentService.listAll().stream().map(enrollmentMapper::toDTO).toList();
    }

    @GetMapping("/by-student/{studentId}")
    public List<EnrollmentDTO> byStudent(@PathVariable Long studentId) {
        return enrollmentService.findByStudent(studentId).stream().map(enrollmentMapper::toDTO).toList();
    }

    @GetMapping("/by-course/{courseId}")
    public List<EnrollmentDTO> byCourse(@PathVariable Long courseId) {
        return enrollmentService.findByCourse(courseId).stream().map(enrollmentMapper::toDTO).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        enrollmentService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}

