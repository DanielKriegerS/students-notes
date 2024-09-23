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
    private final ValidationService validator;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, ValidationService validator) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.validator = validator;
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

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);

        validator.validateNewCourse(course);

        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    public CourseDTO updateCourse(UUID id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        if(courseDTO.name() != null || !courseDTO.name().trim().isEmpty()){
            existingCourse.setName(courseDTO.name());
        }

        if(courseDTO.maxStudents() > 0) {
            existingCourse.setMaxStudents(courseDTO.maxStudents());
        }

        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toDto(updatedCourse);
    }

    public void deleteCourse(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}
