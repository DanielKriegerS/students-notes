package com.danielks.Students_Notes.services;

import com.danielks.Students_Notes.entities.Course;
import com.danielks.Students_Notes.entities.dtos.CourseDTO;
import com.danielks.Students_Notes.entities.mappers.CourseMapper;
import com.danielks.Students_Notes.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CourseDTO getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return courseMapper.toDto(course);
    }

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }
}
