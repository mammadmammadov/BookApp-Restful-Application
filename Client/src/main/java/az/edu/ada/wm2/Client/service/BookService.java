package az.edu.ada.wm2.Client.service;

import az.edu.ada.wm2.Client.exception.CustomClientException;
import az.edu.ada.wm2.Client.exception.CustomResponseErrorHandler;
import az.edu.ada.wm2.Client.model.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final RestTemplate restTemplate;

    //  !! IMPORTANT - BELOW ↓ !!

    // for bookServiceUrl use bookapp instead of localhost while using Docker
    // use localhost instead of bookapp if you want to run these two applications separately from IDE
    private final String bookServiceUrl = "http://bookapp:8080";

    @Autowired
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    }

    public List getAllBooks() {
        return restTemplate.getForObject(bookServiceUrl + "/books", List.class);
    }

    public BookDto getBookById(Long id) {
        return restTemplate.getForObject(bookServiceUrl + "/books/" + id, BookDto.class);
    }

    public BookDto createBook(BookDto bookDto) {
        return restTemplate.postForObject(bookServiceUrl + "/books", bookDto, BookDto.class);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        restTemplate.put(bookServiceUrl + "/books/" + id, bookDto);
        return getBookById(id);
    }

    public BookDto patchBook(Long id, Map<String, Object> updates) {
        try {
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(updates);
            ResponseEntity<BookDto> responseEntity = restTemplate.exchange(
                    bookServiceUrl + "/books/" + id,
                    HttpMethod.PATCH,
                    requestEntity,
                    BookDto.class
            );
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new CustomClientException(HttpStatus.BAD_REQUEST, "Invalid data or request");
        }
    }

    public void deleteBook(Long id) {
        restTemplate.delete(bookServiceUrl + "/books/" + id);
    }
}
