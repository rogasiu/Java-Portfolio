package project.web.app.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import project.web.app.model.Projekt;
import project.web.app.model.Student;
import project.web.app.service.ProjektService;
import project.web.app.service.StudentService;

@Controller
public class ProjectController {
	private ProjektService projektService;
	private StudentService studentService;
	
	//@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacja nie jest potrzebna
	public ProjectController(ProjektService projektService, StudentService studentService) { 
		this.projektService = projektService;
		this.studentService = studentService;
	}
	
								//metodę wywoływamy wpisując w przeglądarce np. http://localhost:8081/projektList lub
	@GetMapping("/projektList") //http://localhost:8081/projektList?page=0&size=10&sort=dataCzasModyfikacji,desc
	public String projektList(Model model, Pageable pageable) {
		//za pomocą zmiennej model i metody addAttribute przekazywane są do widoku obiekty
		//zawierające dane projektów
		model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
		return "projektList"; //metoda zwraca nazwę logiczną widoku, Spring dopisuje nazwę do 
		//odpowiedniego szablonu tj. project-web-app\src\main\resources\templates\projektList.html
	}
	
	@GetMapping("/projektEdit")
	public String projektEdit(@RequestParam(required = false) Integer projektId, Model model, Pageable pageable) {
		if(projektId != null) {
			model.addAttribute("projekt", projektService.getProjekt(projektId).get());
		}else {
			Projekt projekt = new Projekt();
			model.addAttribute("projekt", projekt);
		}
		model.addAttribute("studentt", studentService.getAll(pageable).toList());
		return "projektEdit"; //metoda zwraca logiczną nazwę widoku, Spring dopasuje nazwę do odpowiedniego
		//szablonu tj. project-web-app\src\main\resources\templates\projektEdit.html
	}
	
	@PostMapping(path ="/projektEdit")
	public String projektEditSave(@ModelAttribute @Valid Projekt projekt, BindingResult bindingResult, 
			@RequestParam(value ="studenciId", required = false) String[] studenciId) {
			//parametr BindingResult powinien wystąpić zaraz za parametrem opatrzonym adnotacją @Valid
		if(bindingResult.hasErrors()) {
			return "projektEdit"; //wracamy do okna edycji, jeżeli przesłane dane formularza zawierają błędy
		}
		try {
			projekt.setStudenci(new HashSet<>());
			if(studenciId != null)
			{
				Set<Student> ustawStudenta = projekt.getStudenci();
				for(String stud: studenciId)
				{
					Integer id = Integer.parseInt(stud);
					Optional<Student> pobranyStudent = studentService.getStudentId(id);
					if(pobranyStudent.isPresent())
						ustawStudenta.add(pobranyStudent.get());
				}
			}
			projekt = projektService.setProjekt(projekt);
		} catch (HttpStatusCodeException e) {
			bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
					e.getStatusCode().getReasonPhrase());
			return "projektEdit";
		}
		return "redirect:/projektList"; //przekierowanie do listy projektów, po utworzeniu lub modyfikacji projektu
	}
	
	@PostMapping(params="cancel", path = "/projektEdit") //metoda zostanie wywołana, jeżeli przesłane
	public String projektEditCancel() {			//żadanie będzie zawierało parametr cancel
		return "redirect:/projektList";
	}
	
	@PostMapping(params="delete", path = "/projektEdit")		//metoda zostanie wywołana, jeżeli przesłane 
	public String projektEditDelete(@ModelAttribute Projekt projekt) { //żądanie będzie zawierało parametr delete
		projektService.deleteProjekt(projekt.getProjektId());
		return "redirect:/projektList";
	}
	
}
