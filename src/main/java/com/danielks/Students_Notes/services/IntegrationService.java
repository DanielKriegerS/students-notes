package com.danielks.Students_Notes.services;

import com.danielks.Students_Notes.entities.Course;
import com.danielks.Students_Notes.entities.Student;
import com.danielks.Students_Notes.entities.dtos.StudentDTO;
import com.danielks.Students_Notes.entities.mappers.StudentMapper;
import com.danielks.Students_Notes.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IntegrationService {

    private final StudentRepository studentRepository;
    private final ValidationService validator;
    private final StudentMapper studentMapper;

    public IntegrationService(StudentRepository studentRepository, ValidationService validator, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.validator = validator;
        this.studentMapper = studentMapper;
    }

    public StudentDTO enrollStudentInCourse(UUID studentId, UUID courseId) {
        Student student = validator.validateStudent(studentId);
        Course course = validator.validateCourse(courseId);

        validator.validateEnroll(student, course);

        student.setCourse(course);
        studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    public StudentDTO unenrollStudentFromCourse(UUID studentId, UUID courseId) {
        Student student = validator.validateStudent(studentId);
        Course course = validator.validateCourse(courseId);

        validator.validateUnenroll(student, course);

        student.setCourse(null);
        studentRepository.save(student);

        return studentMapper.toDto(student);
    }
}
