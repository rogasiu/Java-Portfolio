package project.web.app.model;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zadanie {

	private Integer zadanieId;

	private String nazwa;
	
	private Integer kolejnosc;

	private String opis;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime dataCzasDodania;

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
