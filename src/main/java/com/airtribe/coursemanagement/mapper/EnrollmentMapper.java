package com.airtribe.coursemanagement.mapper;

import com.airtribe.coursemanagement.dto.EnrollmentDTO;
import com.airtribe.coursemanagement.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    EnrollmentDTO toDTO(Enrollment enrollment);

    // if needed later: Enrollment toEntity(EnrollmentDTO dto);
}

