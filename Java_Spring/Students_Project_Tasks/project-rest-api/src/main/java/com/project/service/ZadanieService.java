package com.project.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.model.Zadanie;

public interface ZadanieService {
	
	Page<Zadanie> searchZadaniaProjektu(Integer projektId, Pageable pageable);
	Page<Zadanie> getAllZadania(Pageable pageable);
	Zadanie addZadanie(Zadanie zadanie);
	void deleteZadanie(Integer zadanieId);
	Optional<Zadanie> getZadanie(Integer zadanieId);
}
