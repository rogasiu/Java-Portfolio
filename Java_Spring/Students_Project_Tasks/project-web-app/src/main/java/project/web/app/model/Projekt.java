package project.web.app.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Projekt {

	private Integer projektId;

	private String nazwa;
	

	private String opis;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime dataCzasUtworzenia;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime dataCzasModyfikacji;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataOddania;
	

	private List<Zadanie> zadania;
	
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
