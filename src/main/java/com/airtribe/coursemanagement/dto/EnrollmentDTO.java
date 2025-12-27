package com.airtribe.coursemanagement.dto;

import java.time.LocalDate;

public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDate enrolledAt;
    private String status;

    public EnrollmentDTO() {}

    public EnrollmentDTO(Long id, Long studentId, Long courseId, LocalDate enrolledAt, String status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public LocalDate getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDate enrolledAt) { this.enrolledAt = enrolledAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

