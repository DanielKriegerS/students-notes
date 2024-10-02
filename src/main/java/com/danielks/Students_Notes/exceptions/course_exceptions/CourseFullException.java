package com.danielks.Students_Notes.exceptions.course_exceptions;

import com.danielks.Students_Notes.exceptions.InvalidRequestException;

import java.util.UUID;

public class CourseFullException extends InvalidRequestException {
    public CourseFullException(UUID id) {
        super("The course with id " + id + " is full!");
    }}
