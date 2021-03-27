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

import project.web.app.model.Student;

@Service
public class StudentServiceImpl implements StudentService{
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Value("${rest.server.url}") // adres serwera jest wstrzykiwany przez Springa, a jego wartość private 
	String serverUrl; // przechowywana w pliku src/main/resources/application.properties

	private final static String RESOURCE_PATH = "/api/student";
	
	private RestTemplate restTemplate; //obiekt wstrzykiwane poprzez konstruktor, dzięki adnotacjom
	//@Configuration i @Bean zawartym w klasie SecurityConfig
	//Spring utworzy wcześniej obiekt, a adnotacja @Autowired tej klasy
	//wskaże element docelowy wstrzykiwania  (adnotacja może być 
	//pomijana jeżeli w klasie jest tylko jeden konstruktor)
	
	@Autowired
	public StudentServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public Student addStudent(Student student) {
		if(student.getStudentId()!= null) { //modyfikacja istniejącego studenta
			String url = getUriStringComponent(student.getStudentId());
			logger.info("REQUEST -> PUT {}", url);
			restTemplate.put(url, student);
			return student;
		} else { //dodanie nowego studenta
			HttpEntity<Student> request = new HttpEntity<>(student);
			String url = getUriStringComponent();
			logger.info("REQUEST -> POST {}", url);
			URI location = restTemplate.postForLocation(url, request);
			logger.info("REQUEST (location) -> GET {}", location);
			return restTemplate.getForObject(location, Student.class);
		}
	}

	@Override
	public void deleteStudent(Integer studentId) {
		URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(studentId))
				.build()
				.toUri();
		logger.info("REQUEST -> DELETE {}", url);
		restTemplate.delete(url);
	}

	@Override
	public Page<Student> getAll(Pageable pageable) {
		URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
		logger.info("REQUEST -> GET {}", url);
		return getPage(url, restTemplate);
	}

	@Override
	public Optional<Student> getStudentIndeks(String nrIndeksu) {
		URI url = ServiceUtil.getUriComponent(serverUrl,  getResourcePath())
				.build()
				.toUri();
		logger.info("REQUEST -> GET {}", url);
		return Optional.ofNullable(restTemplate.getForObject(url, Student.class));
	}

	@Override
	public Optional<Student> getStudentId(Integer studentId) {
		URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(studentId))
				.build()
				.toUri();
		logger.info("REQUEST -> GET {}", url);
		return Optional.ofNullable(restTemplate.getForObject(url, Student.class));
	}

	@Override
	public Page<Student> searchByIndeks(String nrIndeksu, Pageable pageable) {
		URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(), pageable)
				.queryParam("nrIndeksu", nrIndeksu)
				.build().toUri();
		logger.info("REQUEST -> GET {}", url);
		return getPage(url, restTemplate);
	}

	@Override
	public Page<Student> searchByNazwisko(String nazwisko, Pageable pageable) {
		URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(), pageable)
				.queryParam("nazwisko", nazwisko)
				.build().toUri();
		logger.info("REQUEST -> GET {}", url);
		return getPage(url, restTemplate);
	}
	//metody pomocnicze
		private Page<Student> getPage(URI uri, RestTemplate restTemplate) {
			return ServiceUtil.getPage(uri, restTemplate, new ParameterizedTypeReference<RestResponsePage<Student>>() {});
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
