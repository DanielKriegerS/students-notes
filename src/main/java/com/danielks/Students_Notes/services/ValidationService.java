package com.danielks.Students_Notes.services;

import com.danielks.Students_Notes.entities.Course;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validateNewCourse(Course course) {
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The course has no name!");
        }

        if (course.getMaxStudents() == 0) {
            throw new IllegalArgumentException("The course has no maximum of students!");
        }

        if (course.getMaxStudents() < 0) {
            throw new IllegalArgumentException("The course has an invalid maximum of students!");
        }
    }
}
