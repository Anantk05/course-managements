package com.airtribe.coursemanagement.controller;

import com.airtribe.coursemanagement.dto.StudentDTO;
import com.airtribe.coursemanagement.mapper.StudentMapper;
import com.airtribe.coursemanagement.model.Student;
import com.airtribe.coursemanagement.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping
    public List<StudentDTO> list() {
        return studentService.findAll().stream().map(studentMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> get(@PathVariable Long id) {
        return studentService.findById(id)
                .map(studentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        Student saved = studentService.save(student);
        StudentDTO dto = studentMapper.toDTO(saved);
        return ResponseEntity.created(URI.create("/api/students/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Long id, @RequestBody StudentDTO studentDto) {
        return studentService.findById(id).map(existing -> {
            existing.setFirstName(studentDto.getFirstName());
            existing.setLastName(studentDto.getLastName());
            existing.setEmail(studentDto.getEmail());
            Student updated = studentService.save(existing);
            return ResponseEntity.ok(studentMapper.toDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
