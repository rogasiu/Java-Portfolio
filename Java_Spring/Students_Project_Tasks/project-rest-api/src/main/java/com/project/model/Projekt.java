package com.project.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="projekt") //potrzebne tylko je�eli nazwa tabeli w bazie danych ma by� inna od nazwy klasy
public class Projekt {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="projekt_id") //tylko je�eli nazwa kolumny w bazie danych ma by� inna od nazwy zmiennej
	private Integer projektId;
	
	@NotBlank(message = "Pole nazwa nie mo�e by� puste!")
	@Size(min = 3, max = 50, message = "Nazwa musi zawiera� od {min} do {max} znak�w!")
	@Column(nullable = false, length = 50)
	private String nazwa;
	
	@Size(max = 1000, message = "Opis nie mo�e by� wi�kszy ni� {max}!")
	@Column(length = 1000)
	private String opis;
	
	@CreationTimestamp
	@Column(name = "datacza_utworzenia", nullable = false, updatable = false)
	private LocalDateTime dataCzasUtworzenia;
	
	@UpdateTimestamp 
	@Column(name = "dataczas_modyfikacji", nullable = false)
	private LocalDateTime dataCzasModyfikacji;

	@Column(name = "data_oddania")
	private LocalDate dataOddania;
	
	@OneToMany(mappedBy = "projekt")
	@JsonIgnoreProperties({"projekt"})
	private List<Zadanie> zadania;
	
	@ManyToMany
	@JsonIgnoreProperties({"projekty"})
	@JoinTable(name = "projekt_student",
	joinColumns = {@JoinColumn(name="projekt_id")},
	inverseJoinColumns = {@JoinColumn(name="student_id")})
	 
	private Set<Student> studenci = new HashSet<>();

	public Projekt() {}
	
	public Projekt(String nazwa, String opis, LocalDate dataOddania) {
		this.nazwa = nazwa;
		this.opis = opis;
		this.dataOddania = dataOddania;
	}
	
	public Projekt(Integer projektId, String nazwa, String opis, LocalDateTime dataCzasUtworzenia,LocalDate dataOddania) {
		this.projektId = projektId;
		this.nazwa = nazwa;
		this.opis = opis;
		this.dataOddania = dataOddania;
		this.dataCzasUtworzenia = dataCzasUtworzenia;
	}
	
	public Set<Student> getStudenci() {
		return studenci;
	}

	public void setStudenci(Set<Student> studenci) {
		this.studenci = studenci;
	}

	public LocalDate getDataOddania() {
		return dataOddania;
	}

	public void setDataOddania(LocalDate dataOddania) {
		this.dataOddania = dataOddania;
	}
	
	public Integer getProjektId() {
		return projektId;
	}

	public void setProjektId(Integer projektId) {
		this.projektId = projektId;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public LocalDateTime getDataCzasUtworzenia() {
		return dataCzasUtworzenia;
	}

	public void setDataCzasUtworzenia(LocalDateTime dataCzasUtworzenia) {
		this.dataCzasUtworzenia = dataCzasUtworzenia;
	}

	public LocalDateTime getDataCzasModyfikacji() {
		return dataCzasModyfikacji;
	}

	public void setDataCzasModyfikacji(LocalDateTime dataCzasModyfikacji) {
		this.dataCzasModyfikacji = dataCzasModyfikacji;
	}

	public List<Zadanie> getZadania() {
		return zadania;
	}

	public void setZadania(List<Zadanie> zadania) {
		this.zadania = zadania;
	}
	
}
