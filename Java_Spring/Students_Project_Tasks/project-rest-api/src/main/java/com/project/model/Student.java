package com.project.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "student",
		indexes = { @Index(name = "idx_nazwisko", columnList = "nazwisko", unique = false),
					@Index(name = "idx_nr_indeksu", columnList = "nr_indeksu", unique = true) })
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_id")
	private Integer studentId;
	
	@NotBlank(message = "Pole imię nie może byc puste!")
	@Size(min = 3, max = 50, message = "Imię musi zawierać od {min} do {max} znaków!")
	@Column(nullable = false, length = 50)
	private String imie;
	
	@NotBlank(message = "Pole nazwisko nie może być puste!")
	@Size(min = 3, max = 100, message = "Nazwisko musi zawierać od {min} do {max} znaków!")
	@Column(nullable = false, length = 100)
	private String nazwisko;
	
	@NotBlank(message = "Pole nr Indeksu nie może być puste!")
	@Size(min = 3, max = 20, message = "Nr Indeksu musi zawierać od {min} do {max} znaków!")
	@Column(name = "nr_indeksu", unique = true, nullable = false, length = 20)
	private String nrIndeksu;
	
	@Email(message = "Email powinien być poprawny")
	@Column(length = 50)
	private String email;
	
	@NotNull(message = "Należy wybrać czy student jest stacjonarny")
	@Column(nullable = false)
	private Boolean stacjonarny;
	
	@ManyToMany(mappedBy = "studenci")
	@JsonIgnoreProperties({"studenci"})
	private Set<Projekt> projekty = new HashSet<>();
	
	public Student() {}
	
	public Student(String imie, String nazwisko, String nrIndeksu) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.nrIndeksu = nrIndeksu;
	}
	
	public Student(String imie, String nazwisko, String nrIndeksu, String email, Boolean stacjonarny, Set<Projekt> projekty) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.nrIndeksu = nrIndeksu;
		this.email = email;
		this.stacjonarny = stacjonarny;
		this.projekty = projekty;
	}
	

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNrIndeksu() {
		return nrIndeksu;
	}

	public void setNrIndeksu(String nrIndeksu) {
		this.nrIndeksu = nrIndeksu;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getStacjonarny() {
		return stacjonarny;
	}

	public void setStacjonarny(Boolean stacjonarny) {
		this.stacjonarny = stacjonarny;
	}

	public Set<Projekt> getProjekty() {
		return projekty;
	}

	public void setProjekty(Set<Projekt> projekty) {
		this.projekty = projekty;
	}
	
	
}
