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

import com.project.model.Projekt;
import com.project.service.ProjektService;

// dzi�ki adnotacji @RestController klasa jest traktowana jako zarz�dzany
@RestController // przez Springa REST-owy kontroler obs�uguj�cy sieciowe ��dania
@RequestMapping("/api") // adnotacja @RequestMapping umieszczona w tym miejscu pozwala definiowa�
public class ProjektRestController {
			
	private ProjektService projektService; //serwis jest automatycznie wstrzykiwany poprzez konstruktor

	@Autowired
	public ProjektRestController(ProjektService projektService) {
		this.projektService = projektService;
	}
	
	// PRZED KA�D� Z PONI�SZYCH METOD JEST UMIESZCZONA ADNOTACJA (@GetMapping, PostMapping, ... ), KT�RA OKRE�LA
	// RODZAJ METODY HTTP, A TAK�E ADRES I PARAMETRY ��DANIA
	//Przyk�ad ��dania wywo�uj�cego metod�: GET http://localhost:8080/api/projekty/1
	
	@GetMapping("/projekty/{projektId}")
	ResponseEntity<Projekt> getProjekt(@PathVariable Integer projektId) {// @PathVariable oznacza, �e warto��
		return ResponseEntity.of(projektService.getProjekt(projektId)); // parametru przekazywana jest w �cie�ce
	}
	
	
	// @Valid w��cza automatyczn� walidacj� na podstawie adnotacji zawartych
	// w modelu np. NotNull, Size, NotEmpty itp. (z javax.validation.constraints.*)
	
	@PostMapping(path = "/projekty")
	ResponseEntity<Void> createProjekt(@Valid @RequestBody Projekt projekt) {// @RequestBody oznacza, �e dane // projektu (w formacie JSON) s�
		Projekt createdProjekt = projektService.setProjekt(projekt); // przekazywane w ciele ��dania
		URI location = ServletUriComponentsBuilder.fromCurrentRequest() // link wskazuj�cy utworzony projekt
				.path("/{projektId}").buildAndExpand(createdProjekt.getProjektId()).toUri();
		return ResponseEntity.created(location).build(); // zwracany jest kod odpowiedzi 201 - Created
	} // z linkiem location w nag��wku
	
	
	@PutMapping("/projekty/{projektId}")
	public ResponseEntity<Void> updateProjekt(@Valid @RequestBody Projekt projekt,
			@PathVariable Integer projektId) {
		return projektService.getProjekt(projektId)
				.map(p -> {
					projektService.setProjekt(projekt);
					return new ResponseEntity<Void>(HttpStatus.OK); // 200 (mo�na te� zwraca� 204 - No content)
				})
				.orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
	}
	
	@DeleteMapping("/projekty/{projektId}")
	public ResponseEntity<Void> deleteProjekt(@PathVariable Integer projektId) {
		return projektService.getProjekt(projektId).map(p -> {
			projektService.deleteProjekt(projektId);
			return new ResponseEntity<Void>(HttpStatus.OK); // 200
		}).orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
	}
	
	
//	//Przyk�ad ��dania wywo�uj�cego metod�: http://localhost:8080/api/projekty?page=0&size=10&sort=nazwa,desc
//	@GetMapping(value = "/projekty")
//	Page<Projekt> getProjekty(Pageable pageable) { // @RequestHeader HttpHeaders headers � je�eli potrzebny
//		return projektService.getProjekty(pageable); // by�by nag��wek, wystarczy doda� drug� zmienn� z adnotacj�
//	}
	
	@GetMapping(value = "/projekty")
	Page<Projekt> getProjekty(Pageable pageable) {
		return projektService.getProjekty(pageable);
	}
	
	// Przyk�ad ��dania wywo�uj�cego metod�: GET http://localhost:8080/api/projekty?nazwa=webowa
	// Metoda zostanie wywo�ana tylko, gdy w ��daniu b�dzie przesy�ana warto�� parametru nazwa.
	@GetMapping(value = "/projekty", params="nazwa")
	Page<Projekt> getProjektyByNazwa(@RequestParam String nazwa, Pageable pageable) {
		return projektService.searchByNazwa(nazwa, pageable);
	}
		
}