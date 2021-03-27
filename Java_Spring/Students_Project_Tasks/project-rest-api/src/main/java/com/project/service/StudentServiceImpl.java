package com.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.model.Student;
import com.project.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

	private StudentRepository studentRepository;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@Transactional
	@Override
	public Student addStudent(Student student) {
		// TODO Auto-generated method stub
		Student savedStudent = studentRepository.save(student);
		return savedStudent;
		
	}

	@Override
	public void deleteStudent(Integer studentId) {
		// TODO Auto-generated method stub
		studentRepository.deleteById(studentId);
	}

	@Override
	public Page<Student> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return studentRepository.findAll(pageable);
	}


	@Override
	public Page<Student> searchByIndeks(String nrIndeksu, Pageable pageable) {
		// TODO Auto-generated method stub
		return studentRepository.findByNrIndeksuStartsWith(nrIndeksu, pageable);
	}

	@Override
	public Optional<Student> getStudentIndeks(String nrIndeksu) {
		// TODO Auto-generated method stub
		return studentRepository.findByNrIndeksu(nrIndeksu);
	}

	@Override
	public Page<Student> searchByNazwisko(String nazwisko, Pageable pageable) {
		// TODO Auto-generated method stub
		return studentRepository.findByNazwiskoStartsWithIgnoreCase(nazwisko, pageable);
	}

	@Override
	public Optional<Student> getStudentId(Integer studentId) {
		// TODO Auto-generated method stub
		return studentRepository.findById(studentId);
	}

}
