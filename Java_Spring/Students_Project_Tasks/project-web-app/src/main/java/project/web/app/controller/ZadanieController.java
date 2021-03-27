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

import project.web.app.model.Zadanie;
import project.web.app.service.ZadanieService;

@Controller
public class ZadanieController {
	private ZadanieService zadanieService;
	
	//@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacja nie jest potrzebna
	public ZadanieController(ZadanieService zadanieService) {
		this.zadanieService = zadanieService;
	}
	
	@GetMapping("/zadanieList")
	public String zadanieList(@RequestParam(required = false) Integer projektId, Model model, Pageable pageable) {
		if(projektId != null) {
			model.addAttribute("zadania", zadanieService.searchZadaniaProjektu(projektId, pageable).getContent());
		} else {
			model.addAttribute("zadania", zadanieService.getAllZadania(pageable).getContent());
		}
		return "zadanieList";
	}
	
	@GetMapping("/zadanieEdit")
	public String zadanieEdit(@RequestParam(required = false) Integer zadanieId, Model model) {
		if(zadanieId != null) {
			model.addAttribute("zadanie", zadanieService.getZadanie(zadanieId).get());
		} else {
			Zadanie zadanie = new Zadanie();
			model.addAttribute("zadanie", zadanie);
		}
		return "zadanieEdit";
	}
	
	@PostMapping(path="/zadanieEdit")
	public String zadanieEditSave(@ModelAttribute @Valid Zadanie zadanie, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "zadanieEdit";
		}
		try {
			zadanie = zadanieService.addZadanie(zadanie);
		} catch (HttpStatusCodeException e){
			bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
					e.getStatusCode().getReasonPhrase());
			return "zadanieEdit";
		}
		return "redirect:/zadanieList";
	}
	
	@PostMapping(params="cancel", path = "/zadanieEdit")
	public String zadanieEditCancel() {
		return "redirect:/zadanieList";
	}	
	
	@PostMapping(params="delete", path="/zadanieEdit")
	public String zadanieEditDelete(@ModelAttribute Zadanie zadanie) {
		zadanieService.deleteZadanie(zadanie.getZadanieId());
		return "redirect:/zadanieList";
	}
	
	
	

}
