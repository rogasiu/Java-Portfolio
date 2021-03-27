package com.project.service;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.repository.ProjektRepository;
import com.project.repository.ZadanieRepository;

@Service
public class ProjektServiceImpl implements ProjektService {

	private ProjektRepository projektRepository;
	private ZadanieRepository zadanieRepository;
	
	@Autowired // w tej wersji konstruktora Spring wstrzyknie dwa repozytoria
	public ProjektServiceImpl(ProjektRepository projektRepository, ZadanieRepository zadanieRepo) {
	this.projektRepository = projektRepository;
	this.zadanieRepository = zadanieRepo;
	}
	
	@Override
	public Optional<Projekt> getProjekt(Integer projektId) {
		return projektRepository.findById(projektId);
	}


	@Override
	@Transactional
	public Projekt setProjekt(Projekt projekt) {
		Projekt savedProjekt = projektRepository.save(projekt);
		List<Zadanie> zadania = savedProjekt.getZadania(); 
		if (zadania != null) {
			for (Zadanie zadanie : zadania) {
				zadanie.setProjekt(savedProjekt);
				zadanieRepository.save(zadanie);
			}
		}
		return savedProjekt;
	}

	@Override
	@Transactional
	public void deleteProjekt(Integer projektId) {
		for (Zadanie zadanie : zadanieRepository.findZadaniaProjektu(projektId)) {
			zadanieRepository.delete(zadanie);
			}
			projektRepository.deleteById(projektId);
		
	}

	@Override
	public Page<Projekt> getProjekty(Pageable pageable) {
		// TODO Auto-generated method stub
		return projektRepository.findAll(pageable);
	}

	@Override
	public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
		// TODO Auto-generated method stub
		return projektRepository.findByNazwaContainingIgnoreCase(nazwa, pageable);
	}

}
