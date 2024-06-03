package az.edu.ada.wm2.Client.exception;

import org.springframework.http.HttpStatus;

public class CustomClientException extends RuntimeException {
    private final HttpStatus status;
    private final String responseBody;

    public CustomClientException(HttpStatus status, String responseBody) {
        super(responseBody);
        this.status = status;
        this.responseBody = responseBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
