package com.danielks.Students_Notes.exceptions.student_exceptions;

import com.danielks.Students_Notes.exceptions.InvalidRequestException;

public class InvalidStudentRequestException extends InvalidRequestException {
    public InvalidStudentRequestException(String invalidArgument) { super("The argument " + invalidArgument + " is invalid!"); }
}
