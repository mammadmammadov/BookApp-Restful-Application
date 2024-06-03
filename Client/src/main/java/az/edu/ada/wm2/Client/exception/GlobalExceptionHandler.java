package az.edu.ada.wm2.Client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomClientException.class)
    public ResponseEntity<String> handleCustomClientException(CustomClientException ex) {
        String userFriendlyMessage = getUserFriendlyMessage(ex.getStatus(), ex.getResponseBody());
        return ResponseEntity.status(ex.getStatus()).body(userFriendlyMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getDefaultMessage()).append("\n");
        });
        return new ResponseEntity<>(errors.toString().trim(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }

    private String getUserFriendlyMessage(HttpStatus status, String responseBody) {
        if (status == HttpStatus.NOT_FOUND) {
            return "The requested book was not found.";
        } else if (status == HttpStatus.BAD_REQUEST) {
            return "There was an error with your request: " + responseBody;
        } else {
            return "An unexpected error occurred: " + responseBody;
        }
    }
}
