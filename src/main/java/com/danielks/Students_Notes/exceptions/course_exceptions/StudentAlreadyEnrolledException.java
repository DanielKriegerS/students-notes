package com.danielks.Students_Notes.exceptions.course_exceptions;

import java.util.UUID;

public class StudentAlreadyEnrolledException extends RuntimeException {
    public StudentAlreadyEnrolledException(UUID studentId, UUID courseId) {
        super("Student with ID " + studentId + " is already enrolled in another course (ID: " + courseId + ").");
    }
}
