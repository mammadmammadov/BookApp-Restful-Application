package az.edu.ada.wm2.BookApp.service.impl;

import az.edu.ada.wm2.BookApp.exception.BookNotFoundException;
import az.edu.ada.wm2.BookApp.model.mapper.BookMapper;
import az.edu.ada.wm2.BookApp.model.dto.BookDto;
import az.edu.ada.wm2.BookApp.model.entity.Book;
import az.edu.ada.wm2.BookApp.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks() {

        List<Book> books = Arrays.asList(
                new Book(1L, "Title1", "Author1", "Description1"),
                new Book(2L, "Title2", "Author2", "Description2"),
                new Book(null, "", "", "")
        );

        List<BookDto> bookDtos = books.stream()
                .map(book -> new BookDto(book.getTitle(), book.getAuthor(), book.getDescription()))
                .collect(Collectors.toList());

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.bookListToBookDtoList(books)).thenReturn(bookDtos);

        List<BookDto> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).bookListToBookDtoList(books);
    }


    @Test
    void getBookById_BookExists() {

        Book book = new Book(1L, "Title", "Author", "Description");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.bookToBookDto(book)).thenReturn(new BookDto(book.getTitle(), book.getAuthor(), book.getDescription()));


        BookDto result = bookService.getBookById(1L);


        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        assertEquals("Author", result.getAuthor());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_BookNotExists() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.getBookById(1L));
        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void createBook() {
        // Given
        BookDto bookDto = new BookDto("Title", "Author", "Description");
        Book book = new Book(null, "Title", "Author", "Description");
        Book savedBook = new Book(1L, "Title", "Author", "Description");
        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(bookDto);

        BookDto result = bookService.createBook(bookDto);

        assertNotNull(result);
        assertEquals(bookDto, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_BookExists() {

        Book existingBook = new Book(1L, "Old Title", "Old Author", "Old Description");
        BookDto bookDto = new BookDto("New Title", "New Author", "New Description");
        Book updatedBook = new Book(1L, "New Title", "New Author", "New Description");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.bookToBookDto(updatedBook)).thenReturn(bookDto);


        BookDto result = bookService.updateBook(1L, bookDto);


        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Author", result.getAuthor());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void updateBook_BookNotExists() {

        BookDto bookDto = new BookDto("Title", "Author", "Description");
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.updateBook(1L, bookDto));
        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void patchBook_BookExists() {

        Book existingBook = new Book(1L, "Old Title", "Old Author", "Old Description");
        BookDto existingBookDto = new BookDto("Old Title", "Old Author", "Old Description");
        BookDto updatedBookDto = new BookDto("New Title", "Old Author", "New Description");
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "New Title");
        updates.put("description", "New Description");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookMapper.bookToBookDto(existingBook)).thenReturn(existingBookDto);
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);
        when(bookMapper.bookToBookDto(existingBook)).thenReturn(updatedBookDto);
        BookDto result = bookService.patchBook(1L, updates);

        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("Old Author", result.getAuthor());
        assertEquals("New Description", result.getDescription());
        verify(bookRepository, times(1)).findById(1L); // Adjusting the verification count
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void patchBook_BookNotExists() {

        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "New Title");
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.patchBook(1L, updates));
        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBook_BookExists() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_BookNotExists() {

        when(bookRepository.existsById(1L)).thenReturn(false);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).existsById(1L);
    }


}
