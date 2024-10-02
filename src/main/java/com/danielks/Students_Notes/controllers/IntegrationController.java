package com.danielks.Students_Notes.controllers;

import com.danielks.Students_Notes.entities.dtos.StudentDTO;
import com.danielks.Students_Notes.services.IntegrationService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
