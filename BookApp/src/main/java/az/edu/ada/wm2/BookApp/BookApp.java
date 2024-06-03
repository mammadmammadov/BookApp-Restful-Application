package az.edu.ada.wm2.BookApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BookApp {

	public static void main(String[] args) {
		SpringApplication.run(BookApp.class, args);
	}

}
