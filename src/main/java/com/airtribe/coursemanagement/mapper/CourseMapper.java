package com.airtribe.coursemanagement.mapper;

import com.airtribe.coursemanagement.dto.CourseDTO;
import com.airtribe.coursemanagement.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO toDTO(Course course);
    Course toEntity(CourseDTO dto);
}

