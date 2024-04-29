package danila.mediasoft.test.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ValueAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIllegalState(ValueAlreadyExistsException e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName(), e.getStackTrace()[0].getClassName());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName(), e.getStackTrace()[0].getClassName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName(), e.getStackTrace()[0].getClassName());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectDataType(HttpMessageNotReadableException e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName(), e.getStackTrace()[0].getClassName());
    }
}
