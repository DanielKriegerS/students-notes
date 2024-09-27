package com.danielks.Students_Notes.exceptions.course_exceptions;

import com.danielks.Students_Notes.exceptions.EntityNotFoundException;

import java.util.UUID;

public class CourseNotFoundException extends EntityNotFoundException {
    public CourseNotFoundException(UUID id) { super("Course with ID " + id + " not founded!"); }
}
