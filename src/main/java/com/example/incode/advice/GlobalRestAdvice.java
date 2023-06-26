package com.example.incode.advice;

import com.example.incode.dto.ErrorDto;
import com.example.incode.exception.AlreadyExistsException;
import com.example.incode.exception.NotFoundException;
import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException e) {
        StringBuilder errorMessage = new StringBuilder("Validation error: ");
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }
        return ResponseEntity.badRequest().body(new ErrorDto(errorMessage.toString()));
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleAlreadyExistsException(AlreadyExistsException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage());

        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler({MongoException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleMongoException(MongoException e) {
        String message = "Failed to process request due to database issues";
        ErrorDto errorDto = new ErrorDto(message);

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
