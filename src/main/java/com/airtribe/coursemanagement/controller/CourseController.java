package com.airtribe.coursemanagement.controller;

import com.airtribe.coursemanagement.dto.CourseDTO;
import com.airtribe.coursemanagement.mapper.CourseMapper;
import com.airtribe.coursemanagement.model.Course;
import com.airtribe.coursemanagement.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public List<CourseDTO> list() {
        return courseService.findAll().stream().map(courseMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> get(@PathVariable Long id) {
        return courseService.findById(id)
                .map(courseMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDto) {
        Course course = courseMapper.toEntity(courseDto);
        Course saved = courseService.save(course);
        CourseDTO dto = courseMapper.toDTO(saved);
        return ResponseEntity.created(URI.create("/api/courses/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable Long id, @RequestBody CourseDTO courseDto) {
        return courseService.findById(id).map(existing -> {
            existing.setTitle(courseDto.getTitle());
            existing.setDescription(courseDto.getDescription());
            existing.setStartDate(courseDto.getStartDate());
            existing.setEndDate(courseDto.getEndDate());
            Course updated = courseService.save(existing);
            return ResponseEntity.ok(courseMapper.toDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
