package com.danielks.Students_Notes.controllers.exceptionHandlers;


import com.danielks.Students_Notes.controllers.CourseController;
import com.danielks.Students_Notes.controllers.IntegrationController;
import com.danielks.Students_Notes.entities.dtos.CourseErrorDTO;
import com.danielks.Students_Notes.exceptions.course_exceptions.CourseFullException;
import com.danielks.Students_Notes.exceptions.course_exceptions.CourseNotFoundException;
import com.danielks.Students_Notes.exceptions.course_exceptions.InvalidCourseRequestException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ControllerAdvice(assignableTypes = {CourseController.class, IntegrationController.class})
public class CourseExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<EntityModel<CourseErrorDTO>> handleCourseNotFound(CourseNotFoundException e) {
        CourseErrorDTO errorResponse = new CourseErrorDTO("Course not found: " + e.getMessage(), HttpStatus.NOT_FOUND.value());
        Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(EntityModel.of(errorResponse, allCoursesLink));
    }

    @ExceptionHandler(InvalidCourseRequestException.class)
    public ResponseEntity<EntityModel<CourseErrorDTO>> handleInvalidRequest(InvalidCourseRequestException e) {
        CourseErrorDTO errorResponse = new CourseErrorDTO("Invalid request: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
        Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityModel.of(errorResponse, allCoursesLink));
    }

    @ExceptionHandler(CourseFullException.class)
    public ResponseEntity<EntityModel<CourseErrorDTO>> handleCourseFull(CourseFullException e) {
        CourseErrorDTO errorResponse = new CourseErrorDTO("Course is full: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
        Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityModel.of(errorResponse, allCoursesLink));
    }
}
