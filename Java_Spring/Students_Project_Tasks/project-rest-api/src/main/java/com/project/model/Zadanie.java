package com.project.model;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="zadanie")
public class Zadanie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="zadanie_id")
	private Integer zadanieId;
	
	@NotBlank(message = "Pole nazwa nie może być puste!")
	@Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
	@Column(nullable = false, length = 50)
	private String nazwa;
	
	@Column
	private Integer kolejnosc;
	
	@Size(max = 1000, message = "Opis nie może być większy niż {max}!")
	@Column(length = 1000)
	private String opis;
	
	@CreationTimestamp
	@Column(name="dataczas_dodania", nullable = false)
	private LocalDateTime dataCzasDodania;
	
	@ManyToOne
	@JsonIgnoreProperties({"zadania"})
	@JoinColumn(name = "projekt_id")
	private Projekt projekt;
	
	public Zadanie() {}
	
	public Zadanie(String nazwa, String opis, Integer kolejnosc) {
		this.nazwa = nazwa;
		this.opis = opis;
		this.kolejnosc = kolejnosc;
	}
	
	public Zadanie(String nazwa, String opis, Integer kolejnosc, Projekt projekt) {
		this.nazwa = nazwa;
		this.opis = opis;
		this.kolejnosc = kolejnosc;
		this.projekt = projekt;
	}
	
	public Integer getZadanieId() {
		return zadanieId;
	}

	public void setZadanieId(Integer zadanieId) {
		this.zadanieId = zadanieId;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public Integer getKolejnosc() {
		return kolejnosc;
	}

	public void setKolejnosc(Integer kolejnosc) {
		this.kolejnosc = kolejnosc;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public LocalDateTime getDataCzasDodania() {
		return dataCzasDodania;
	}

	public void setDataCzasDodania(LocalDateTime dataCzasDodania) {
		this.dataCzasDodania = dataCzasDodania;
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}


	
	
}
