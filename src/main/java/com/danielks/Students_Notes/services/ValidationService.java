package com.danielks.Students_Notes.services;

import com.danielks.Students_Notes.entities.Course;
import com.danielks.Students_Notes.entities.Student;
import com.danielks.Students_Notes.exceptions.course_exceptions.InvalidCourseRequestException;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validateNewCourse(Course course) {
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new InvalidCourseRequestException("name");
        }

        if (course.getMaxStudents() == 0 || course.getMaxStudents() <= 0) {
            throw new InvalidCourseRequestException("max of students");
        }
    }

    public void validateNewStudent(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The student has no name!");
        }

        if (student.getAge() < 15) {
            throw new IllegalArgumentException("The age is not suficient to the courses!");
        }
    }
}
