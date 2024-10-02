package com.danielks.Students_Notes.services;

import com.danielks.Students_Notes.entities.Course;
import com.danielks.Students_Notes.entities.Student;
import com.danielks.Students_Notes.exceptions.course_exceptions.*;
import com.danielks.Students_Notes.exceptions.student_exceptions.InvalidStudentRequestException;
import com.danielks.Students_Notes.exceptions.student_exceptions.StudentNotFoundException;
import com.danielks.Students_Notes.repositories.CourseRepository;
import com.danielks.Students_Notes.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidationService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public ValidationService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public void validateNewCourse(Course course) {
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new InvalidCourseRequestException("name");
        }

        if (course.getMaxStudents() <= 0) {
            throw new InvalidCourseRequestException("max of students");
        }
    }

    public void validateNewStudent(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new InvalidStudentRequestException("The student has no name!");
        }

        if (student.getAge() < 15) {
            throw new InvalidStudentRequestException("The age is not sufficient for the courses!");
        }
    }

    public Student validateStudent(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    public Course validateCourse(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public void validateEnroll(Student student, Course course) {
        if (course.getStudents().size() >= course.getMaxStudents()) {
            throw new CourseFullException(course.getId());
        }

        if (student.getCourse() != null && !student.getCourse().getId().equals(course.getId())) {
            throw new StudentAlreadyEnrolledException(student.getId(), student.getCourse().getId());
        }
    }

    public void validateUnenroll(Student student, Course course) {
        if (student.getCourse() == null || !student.getCourse().equals(course)) {
            throw new StudentNotEnrolledException(student.getId(), course.getId());
        }
    }
}
