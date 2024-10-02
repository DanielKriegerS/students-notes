package com.danielks.Students_Notes.controllers;

import com.danielks.Students_Notes.entities.dtos.StudentDTO;
import com.danielks.Students_Notes.services.IntegrationService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/enrollments")
public class IntegrationController {

    private final IntegrationService integrationService;

    public IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<?>> enrollStudentInCourse(
            @RequestParam UUID studentId,
            @RequestParam UUID courseId) {

        StudentDTO enrolledStudent = integrationService.enrollStudentInCourse(studentId, courseId);

        Link selfLink = linkTo(methodOn(IntegrationController.class).enrollStudentInCourse(studentId, courseId)).withSelfRel();
        Link studentLink = linkTo(methodOn(StudentController.class).getStudentById(enrolledStudent.id())).withRel("student-link");
        Link courseLink = linkTo(methodOn(CourseController.class).getCourseById(courseId)).withRel("course-link");

        EntityModel<StudentDTO> resource = EntityModel.of(enrolledStudent, selfLink, studentLink, courseLink);

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping
    public ResponseEntity<EntityModel<?>> unenrollStudentFromCourse(
            @RequestParam UUID studentId,
            @RequestParam UUID courseId) {

        StudentDTO unenrolledStudent = integrationService.unenrollStudentFromCourse(studentId, courseId);

        Link selfLink = linkTo(methodOn(IntegrationController.class).unenrollStudentFromCourse(studentId, courseId)).withSelfRel();
        Link studentLink = linkTo(methodOn(StudentController.class).getStudentById(unenrolledStudent.id())).withRel("student-link");
        Link courseLink = linkTo(methodOn(CourseController.class).getCourseById(courseId)).withRel("course-link");

        EntityModel<StudentDTO> resource = EntityModel.of(unenrolledStudent, selfLink, studentLink, courseLink);

        return ResponseEntity.ok(resource);
    }
}
