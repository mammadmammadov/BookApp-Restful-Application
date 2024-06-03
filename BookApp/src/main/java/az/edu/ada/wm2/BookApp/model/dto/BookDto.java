package az.edu.ada.wm2.BookApp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author must be less than 50 characters")
    private String author;

    @NotBlank(message = "Description is required")
    @Size(max = 200, message = "Description must be less than 200 characters")
    private String description;

    public BookDto(String new_title, String new_author, String new_description) {
        this.title = new_title;
        this.author = new_author;
        this.description = new_description;
    }
}
