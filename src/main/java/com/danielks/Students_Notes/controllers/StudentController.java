package com.danielks.Students_Notes.controllers;

import com.danielks.Students_Notes.entities.dtos.StudentDTO;
import com.danielks.Students_Notes.services.StudentService;
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
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<StudentDTO>>> getAllStudents() {
        List<EntityModel<StudentDTO>> students = service.getAllStudents().stream()
                .map(studentDTO -> EntityModel.of(studentDTO,
                        linkTo(methodOn(StudentController.class).getStudentById(studentDTO.id())).withRel("student-link")))
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();

        CollectionModel<EntityModel<StudentDTO>> resource = CollectionModel.of(students, selfLink);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<StudentDTO>> getStudentById(@PathVariable UUID id) {
        StudentDTO studentDTO = service.getStudentById(id);
        if (studentDTO == null) {
            return ResponseEntity.notFound().build();
        }

        Link selfLink = linkTo(methodOn(StudentController.class).getStudentById(id)).withSelfRel();
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        EntityModel<StudentDTO> resource = EntityModel.of(studentDTO, selfLink, allStudentsLink);
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<StudentDTO>> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = service.createStudent(studentDTO);

        Link selfLink = linkTo(methodOn(StudentController.class).getStudentById(createdStudent.id())).withSelfRel();
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        EntityModel<StudentDTO> resource = EntityModel.of(createdStudent, selfLink, allStudentsLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<StudentDTO>> updateStudent(@PathVariable UUID id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = service.updateStudent(id, studentDTO);

        Link selfLink = linkTo(methodOn(StudentController.class).getStudentById(id)).withSelfRel();
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        EntityModel<StudentDTO> resource = EntityModel.of(updatedStudent, selfLink, allStudentsLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable UUID id) {
        service.deleteStudent(id);
        Link allStudentsLink = linkTo(methodOn(StudentController.class).getAllStudents()).withRel("all-students");

        return ResponseEntity.noContent().header("Link", allStudentsLink.toUri().toString()).build();
    }
}
