package com.danielks.Students_Notes.controllers;

import com.danielks.Students_Notes.entities.dtos.CourseDTO;
import com.danielks.Students_Notes.entities.dtos.CourseErrorDTO;
import com.danielks.Students_Notes.exceptions.course_exceptions.CourseNotFoundException;
import com.danielks.Students_Notes.services.CourseService;
import jakarta.validation.constraints.Pattern;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<?>> getCourseById(@PathVariable UUID id) {
        try {
            CourseDTO courseDTO = courseService.getCourseById(id);
            if (courseDTO == null) {
                throw new CourseNotFoundException(id);
            }

            Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(id)).withSelfRel();
            Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

            EntityModel<CourseDTO> resource = EntityModel.of(courseDTO, selfLink, allCoursesLink);

            return ResponseEntity.ok(resource);
        } catch (CourseNotFoundException e) {
            CourseErrorDTO errorResponse = new CourseErrorDTO("Course not found", HttpStatus.NOT_FOUND.value());
            Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(EntityModel.of(errorResponse, allCoursesLink));
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CourseDTO>>> getAllCourses() {
        List<EntityModel<CourseDTO>> courses = courseService.getAllCourses().stream()
                .map(courseDTO -> EntityModel.of(courseDTO,
                        linkTo(methodOn(CourseController.class).getCourseById(courseDTO.id())).withRel("course-link")))
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(CourseController.class).getAllCourses()).withSelfRel();

        CollectionModel<EntityModel<CourseDTO>> resource = CollectionModel.of(courses, selfLink);

        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CourseDTO>> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO);

        Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(createdCourse.id())).withSelfRel();
        Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

        EntityModel<CourseDTO> resource = EntityModel.of(createdCourse, selfLink, allCoursesLink);

        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<?>> updateCourse(@PathVariable UUID id, @RequestBody CourseDTO courseDTO) {
        try {
            CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
            if (updatedCourse == null) {
                throw new CourseNotFoundException(id);
            }

            Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(id)).withSelfRel();
            Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

            EntityModel<CourseDTO> resource = EntityModel.of(updatedCourse, selfLink, allCoursesLink);

            return ResponseEntity.ok(resource);
        } catch (CourseNotFoundException e) {
            CourseErrorDTO errorResponse = new CourseErrorDTO("Course not found", HttpStatus.NOT_FOUND.value());
            Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(EntityModel.of(errorResponse, allCoursesLink));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable UUID id) {
        try {
            courseService.deleteCourse(id);
            Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

            return ResponseEntity.noContent().header("Link", allCoursesLink.toUri().toString()).build();
        } catch (CourseNotFoundException e) {
            CourseErrorDTO errorResponse = new CourseErrorDTO("Course not found", HttpStatus.NOT_FOUND.value());
            Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(EntityModel.of(errorResponse, allCoursesLink));
        }
    }

}
