package com.airtribe.coursemanagement.mapper;

import com.airtribe.coursemanagement.dto.StudentDTO;
import com.airtribe.coursemanagement.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toDTO(Student student);
    Student toEntity(StudentDTO dto);
}

