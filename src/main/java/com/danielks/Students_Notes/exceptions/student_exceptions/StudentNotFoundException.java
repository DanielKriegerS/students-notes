package com.danielks.Students_Notes.exceptions.student_exceptions;

import com.danielks.Students_Notes.exceptions.EntityNotFoundException;

import java.util.UUID;

public class StudentNotFoundException extends EntityNotFoundException {
    public StudentNotFoundException(UUID id) { super("Student with id " + id + " not founded!"); }
}
