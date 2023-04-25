package com.example.bookserver.advice;

import com.example.bookserver.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
    public class ValidationExceptionHandler {

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(ConstraintViolationException.class)
        public MessageResponse handleValidationExceptions(ConstraintViolationException ex) {
            return new MessageResponse("Ошибка валидации 1 " + ex.getMessage());
        }


        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public MessageResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new MessageResponse("Ошибка валидации 2 "  + ex.getMessage());
    }
}
