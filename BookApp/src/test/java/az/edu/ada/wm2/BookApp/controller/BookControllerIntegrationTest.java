package az.edu.ada.wm2.BookApp.controller;

import az.edu.ada.wm2.BookApp.model.dto.BookDto;
import az.edu.ada.wm2.BookApp.model.entity.Book;
import az.edu.ada.wm2.BookApp.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        Book book = new Book(null, "Test Book", "Test Author", "Test Description");
        bookRepository.save(book);
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    void shouldGetBookById() throws Exception {
        Book book = bookRepository.findAll().get(0);
        mockMvc.perform(get("/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void shouldCreateBook() throws Exception {
        BookDto bookDto = new BookDto("New Book", "New Author", "New Description");
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Book book = bookRepository.findAll().get(0);
        BookDto bookDto = new BookDto("Updated Book", "Updated Author", "Updated Description");
        mockMvc.perform(put("/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void shouldPatchBook() throws Exception {
        Book book = bookRepository.findAll().get(0);
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Patched Book");
        mockMvc.perform(patch("/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Patched Book"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        Book book = bookRepository.findAll().get(0);
        mockMvc.perform(delete("/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
