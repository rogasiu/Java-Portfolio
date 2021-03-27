package com.project.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.model.Student;

public interface StudentService {
	Student addStudent(Student student);
	void deleteStudent(Integer studentId);
	Page<Student> getAll(Pageable pageable);
	Optional<Student> getStudentIndeks(String nrIndeksu);
	Optional<Student> getStudentId(Integer studentId);
	Page<Student> searchByIndeks(String nrIndeksu, Pageable pageable);
	Page<Student> searchByNazwisko(String nazwisko, Pageable pageable);

}
