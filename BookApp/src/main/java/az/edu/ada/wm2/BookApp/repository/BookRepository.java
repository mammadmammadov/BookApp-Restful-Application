package az.edu.ada.wm2.BookApp.repository;

import az.edu.ada.wm2.BookApp.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
