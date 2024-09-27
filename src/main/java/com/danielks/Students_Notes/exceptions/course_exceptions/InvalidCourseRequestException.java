package com.danielks.Students_Notes.exceptions.course_exceptions;

import com.danielks.Students_Notes.exceptions.InvalidRequestException;

public class InvalidCourseRequestException extends InvalidRequestException {
    public InvalidCourseRequestException(String invalidArgument) {
        super("The argument " + invalidArgument + " is invalid!");
    }
}
