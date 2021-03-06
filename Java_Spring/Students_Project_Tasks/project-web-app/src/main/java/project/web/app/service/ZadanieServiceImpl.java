package project.web.app.service;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import project.web.app.model.Zadanie;

@Service
public class ZadanieServiceImpl implements ZadanieService{
	private static final Logger logger = LoggerFactory.getLogger(ZadanieServiceImpl.class);

	@Value("${rest.server.url}") // adres serwera jest wstrzykiwany przez Springa, a jego wartość private 
	String serverUrl; // przechowywana w pliku src/main/resources/application.properties
	
	private final static String RESOURCE_PATH = "/api/zadania";
	
	private RestTemplate restTemplate; //obiekt wstrzykiwane poprzez konstruktor, dzięki adnotacjom
										//@Configuration i @Bean zawartym w klasie SecurityConfig
										//Spring utworzy wcześniej obiekt, a adnotacja @Autowired tej klasy
										//wskaże element docelowy wstrzykiwania  (adnotacja może być 
										//pomijana jeżeli w klasie jest tylko jeden konstruktor)
	@Autowired
	public ZadanieServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public Page<Zadanie> searchZadaniaProjektu(Integer projektId, Pageable pageable) {
		URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(), pageable)
				.queryParam("projektId", projektId)
				.build().toUri();
		logger.info("REQUEST -> GET {}", url);
		return getPage(url, restTemplate);
	}

	@Override
	public Zadanie addZadanie(Zadanie zadanie) {
		if(zadanie.getZadanieId()!= null) { // modyfikacja istniejącego zadania
			String url = getUriStringComponent(zadanie.getZadanieId());
			logger.info("REQUEST -> PUT {}", url);
			restTemplate.put(url, zadanie);
			return zadanie;
		}else { //utworzenie nowego zadania
			HttpEntity<Zadanie> request = new HttpEntity<>(zadanie);
			String url = getUriStringComponent();
			logger.info("REQUEST -> POST {}", url);
			URI location = restTemplate.postForLocation(url, request);
			logger.info("REQUEST (location) -> GET {}", location);
			return restTemplate.getForObject(location, Zadanie.class);
		}
	}

	@Override
	public void deleteZadanie(Integer zadanieId) {
		URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(zadanieId))
				.build()
				.toUri();
		logger.info("REQUEST -> DELETE {}", url);
		restTemplate.delete(url);
		
	}
	@Override
	public Page<Zadanie> getAllZadania(Pageable pageable) {
		URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
		logger.info("REQUEST -> GET {}", url);
		return getPage(url, restTemplate);
	}

	@Override
	public Optional<Zadanie> getZadanie(Integer zadanieId) {
		URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(zadanieId))
				.build()
				.toUri();
		logger.info("REQUEST -> GET {}", url);
		return Optional.ofNullable(restTemplate.getForObject(url, Zadanie.class));
	}
	
	//metody pomocnicze
	private Page<Zadanie> getPage(URI uri, RestTemplate restTemplate) {
		return ServiceUtil.getPage(uri, restTemplate, new ParameterizedTypeReference<RestResponsePage<Zadanie>>() {});
	}
	
	private String getResourcePath() {
		return RESOURCE_PATH;
	}
	private String getResourcePath(Integer id) { 
		return RESOURCE_PATH + "/" + id;
	}
	private String getUriStringComponent() { 
		return serverUrl + getResourcePath();
	}
	private String getUriStringComponent(Integer id) { 
		return serverUrl + getResourcePath(id);
	}

	
	

}
