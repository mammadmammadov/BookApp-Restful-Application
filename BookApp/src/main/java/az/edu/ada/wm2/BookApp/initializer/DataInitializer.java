package az.edu.ada.wm2.BookApp.initializer;

import az.edu.ada.wm2.BookApp.model.entity.Book;
import az.edu.ada.wm2.BookApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Autowired
    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        bookRepository.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", "A novel set in the Roaring Twenties."));
        bookRepository.save(new Book("To Kill a Mockingbird", "Harper Lee", "A novel about racial injustice in the Deep South."));
        bookRepository.save(new Book("1984", "George Orwell", "A dystopian novel about totalitarianism."));
        bookRepository.save(new Book("Pride and Prejudice", "Jane Austen", "A classic romance novel."));
        bookRepository.save(new Book("Moby Dick", "Herman Melville", "A novel about the voyage of the whaling ship Pequod."));
    }
}
