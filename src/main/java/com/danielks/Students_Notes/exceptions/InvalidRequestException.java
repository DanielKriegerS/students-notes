package com.danielks.Students_Notes.exceptions;

import com.sun.jdi.request.InvalidRequestStateException;

public class InvalidRequestException extends InvalidRequestStateException {
    public InvalidRequestException(String message) { super(message); }
}
