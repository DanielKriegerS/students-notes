package com.danielks.Students_Notes.exceptions.course_exceptions;

import java.util.UUID;

public class StudentNotEnrolledException extends RuntimeException {
    public StudentNotEnrolledException(UUID studentId, UUID courseId) {
        super("Student with ID " + studentId + " is not enrolled in the course with ID: " + courseId + ".");
    }
}
