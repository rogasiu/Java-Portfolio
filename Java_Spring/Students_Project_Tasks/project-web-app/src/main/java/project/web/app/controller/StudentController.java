package project.web.app.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import project.web.app.model.Student;
import project.web.app.service.StudentService;

@Controller
public class StudentController {
	private StudentService studentService;
	
	//@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacja nie jest potrzebna
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping("/studentList")
	public String studentList(Model model, Pageable pageable) {
		model.addAttribute("studenci", studentService.getAll(pageable).getContent());
		return "studentList"; //project-web-app\src\main\resources\templates\studentList.html
	}
	
	@GetMapping("/studentEdit")
	public String studentEdit(@RequestParam(required = false) Integer studentId, Model model) {
		if(studentId != null) {
			model.addAttribute("student", studentService.getStudentId(studentId).get());
		}else {
			Student student = new Student();
			model.addAttribute("student", student);
		}
		return "studentEdit";
	}
	
	@PostMapping(path="/studentEdit")
	public String studentEditSave(@ModelAttribute @Valid Student student, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "studentEdit";
		}
		try {
			student = studentService.addStudent(student);
		} catch (HttpStatusCodeException e){
			bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
					e.getStatusCode().getReasonPhrase());
			return "studentEdit";
		}
		return "redirect:/studentList";
	}
	
	@PostMapping(params="cancel", path="/studentEdit")
	public String studentEditCancel() {
		return "redirect:/studentList";
	}
	
	@PostMapping(params="delete", path="/studentEdit")
	public String studentEditDelete(@ModelAttribute Student student) {
		studentService.deleteStudent(student.getStudentId());
		return "redirect:/studentList";
	}
	
	
}
