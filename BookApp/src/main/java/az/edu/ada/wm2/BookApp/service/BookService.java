// BookService.java
package az.edu.ada.wm2.BookApp.service;

import az.edu.ada.wm2.BookApp.model.dto.BookDto;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(Long id, BookDto bookDto);

    BookDto patchBook(Long id, Map<String, Object> updates);


    void deleteBook(Long id);
}
