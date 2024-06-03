package az.edu.ada.wm2.BookApp.model.mapper;

import az.edu.ada.wm2.BookApp.model.dto.BookDto;
import az.edu.ada.wm2.BookApp.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description", target = "description")
    Book bookDtoToBook(BookDto bookDto);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description", target = "description")
    BookDto bookToBookDto(Book book);

    List<BookDto> bookListToBookDtoList(List<Book> books);
}
