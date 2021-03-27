package com.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.model.Zadanie;
import com.project.repository.ZadanieRepository;

@Service
public class ZadanieServiceImpl implements ZadanieService{
	
	private ZadanieRepository zadanieRepository;
	
	
	@Autowired
	public ZadanieServiceImpl(ZadanieRepository zadanieRepository) {
		this.zadanieRepository = zadanieRepository;
	}

	@Override
	public Page<Zadanie> searchZadaniaProjektu(Integer projektId, Pageable pageable) {
		// TODO Auto-generated method stub
		return zadanieRepository.findZadaniaProjektu(projektId, pageable);
	}

	@Override
	public Zadanie addZadanie(Zadanie zadanie) {
		// TODO Auto-generated method stub
		Zadanie savedZadanie =  zadanieRepository.save(zadanie);
		return savedZadanie;
	}

	@Override
	public void deleteZadanie(Integer zadanieId) {
		// TODO Auto-generated method stub
		zadanieRepository.deleteById(zadanieId);
		
	}

	@Override
	public Optional<Zadanie> getZadanie(Integer zadanieId) {
		// TODO Auto-generated method stub
		return zadanieRepository.findById(zadanieId);
	}

	@Override
	public Page<Zadanie> getAllZadania(Pageable pageable) {
		// TODO Auto-generated method stub
		return zadanieRepository.findAll(pageable);
	}

}
