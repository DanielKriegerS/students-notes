package com.danielks.Students_Notes.controllers;

import com.danielks.Students_Notes.entities.dtos.CourseDTO;
import com.danielks.Students_Notes.services.CourseService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        CourseDTO courseDTO = courseService.getCourseById(id);
        Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(id)).withSelfRel();
        Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

        EntityModel<CourseDTO> resource = EntityModel.of(courseDTO, selfLink, allCoursesLink);
        return ResponseEntity.ok(resource);
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
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(id)).withSelfRel();
        Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

        EntityModel<CourseDTO> resource = EntityModel.of(updatedCourse, selfLink, allCoursesLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        Link allCoursesLink = linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses");

        return ResponseEntity.noContent().header("Link", allCoursesLink.toUri().toString()).build();
    }
}
