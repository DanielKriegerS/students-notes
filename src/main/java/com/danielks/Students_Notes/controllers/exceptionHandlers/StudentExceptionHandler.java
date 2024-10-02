package com.danielks.Students_Notes.controllers.exceptionHandlers;

import com.danielks.Students_Notes.controllers.IntegrationController;
import com.danielks.Students_Notes.controllers.StudentController;
import com.danielks.Students_Notes.entities.dtos.StudentErrorDTO;
import com.danielks.Students_Notes.exceptions.course_exceptions.StudentAlreadyEnrolledException;
import com.danielks.Students_Notes.exceptions.course_exceptions.StudentNotEnrolledException;
import com.danielks.Students_Notes.exceptions.student_exceptions.InvalidStudentRequestException;
import com.danielks.Students_Notes.exceptions.student_exceptions.StudentNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ControllerAdvice(assignableTypes = {StudentController.class, IntegrationController.class})
public class StudentExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<EntityModel<StudentErrorDTO>> handleStudentNotFound(StudentNotFoundException e) {
        StudentErrorDTO errorResponse = new StudentErrorDTO("Student not found: " + e.getMessage(), HttpStatus.NOT_FOUND.value());
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(EntityModel.of(errorResponse, allStudentsLink));
    }

    @ExceptionHandler(InvalidStudentRequestException.class)
    public ResponseEntity<EntityModel<StudentErrorDTO>> handleInvalidRequest(InvalidStudentRequestException e) {
        StudentErrorDTO errorResponse = new StudentErrorDTO("Invalid request: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityModel.of(errorResponse, allStudentsLink));
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<EntityModel<StudentErrorDTO>> handleStudentAlreadyEnrolled(StudentAlreadyEnrolledException e) {
        StudentErrorDTO errorResponse = new StudentErrorDTO("Student already enrolled in another course: " + e.getMessage(), HttpStatus.CONFLICT.value());
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(EntityModel.of(errorResponse, allStudentsLink));
    }

    @ExceptionHandler(StudentNotEnrolledException.class)
    public ResponseEntity<EntityModel<StudentErrorDTO>> handleStudentNotEnrolled(StudentNotEnrolledException e) {
        StudentErrorDTO errorResponse = new StudentErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityModel.of(errorResponse, allStudentsLink));
    }

}
