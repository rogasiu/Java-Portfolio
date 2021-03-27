package com.project.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.project.model.Student;
import com.project.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentRestController {

	
		private StudentService studentService;
		
		@Autowired
		public StudentRestController(StudentService studentService) {
			this.studentService = studentService;
		}
 		@DeleteMapping("/student/{studentId}")
		public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
 			return studentService.getStudentId(studentId).map(p -> {
 				studentService.deleteStudent(studentId);
 				return new ResponseEntity<Void>(HttpStatus.OK); //200
 			}).orElseGet(() -> ResponseEntity.notFound().build()); //404 - Not found)
 		}
 		
 		@GetMapping("/student/{studentId}")
 		ResponseEntity<Student> getStudent(@PathVariable Integer studentId) {// @PathVariable oznacza, �e warto��
 			return ResponseEntity.of(studentService.getStudentId(studentId)); // parametru przekazywana jest w �cie�ce
 		}
 		
 		@GetMapping(value = "/student")
 		Page<Student> getStudenci(Pageable pageable) {
 			return studentService.getAll(pageable);
 		}
 		
 		@PostMapping(path= "/student")
 		ResponseEntity<Void> createStudent(@Valid @RequestBody Student student) {
 			Student createdStudent = studentService.addStudent(student);
 			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
 					.path("/{studentId}").buildAndExpand(createdStudent.getStudentId()).toUri();
 			return ResponseEntity.created(location).build();
 		}
 		
 		@PutMapping("/student/{studentId}")
 		public ResponseEntity<Void> updateStudent(@Valid @RequestBody Student student, @PathVariable Integer studentId){
 			return studentService.getStudentId(studentId)
 					.map(s -> {
 						studentService.addStudent(student);
 						return new ResponseEntity<Void>(HttpStatus.OK);
 					})
 					.orElseGet(() -> ResponseEntity.notFound().build());
 		}
		
		@GetMapping(value= "/student", params="nrIndeksu")
		Page<Student> getStudentByIndeks(@RequestParam String nrIndeksu, Pageable pageable) {
			return studentService.searchByIndeks(nrIndeksu, pageable);
		}
		
		@GetMapping(value="/student", params="nazwisko")
		Page<Student> getStudentByNazwisko(@RequestParam String nazwisko, Pageable pageable) {
			return studentService.searchByNazwisko(nazwisko, pageable);
		}
}
