package az.edu.ada.wm2.BookApp.service.impl;

import az.edu.ada.wm2.BookApp.exception.BookNotFoundException;
import az.edu.ada.wm2.BookApp.model.dto.BookDto;
import az.edu.ada.wm2.BookApp.model.entity.Book;
import az.edu.ada.wm2.BookApp.model.mapper.BookMapper;
import az.edu.ada.wm2.BookApp.repository.BookRepository;
import az.edu.ada.wm2.BookApp.service.BookService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookMapper.bookListToBookDtoList(bookRepository.findAll());
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto createBook(BookDto bookDTO) {
        Book book = bookMapper.bookDtoToBook(bookDTO);
        return bookMapper.bookToBookDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setDescription(bookDTO.getDescription());

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.bookToBookDto(updatedBook);
    }

    @Override
    public BookDto patchBook(Long id, Map<String, Object> updates) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        try {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "title" -> {
                        validateField("title", (String) value);
                        existingBook.setTitle((String) value);
                    }
                    case "author" -> {
                        validateField("author", (String) value);
                        existingBook.setAuthor((String) value);
                    }
                    case "description" -> {
                        validateField("description", (String) value);
                        existingBook.setDescription((String) value);
                    }
                    default -> throw new IllegalArgumentException("Invalid field: " + key);
                }
            });
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.bookToBookDto(updatedBook);
    }

    private void validateField(String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null, empty, or blank");
        }
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
