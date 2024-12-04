package danila.mediasoft.test.warehouse.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String message;
    private String exceptionName;
    private String className;
    private LocalDateTime time;

    public ErrorResponse(String message, String exceptionName, String exceptionClass) {
        this.message = message;
        this.className = exceptionClass;
        this.exceptionName = exceptionName;
        this.time = LocalDateTime.now();
    }
}
