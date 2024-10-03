package com.eShelf.info.e.library.exception;

import com.eShelf.info.e.library.dto.ExceptionHandlerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ExceptionHandlerDto> handleIdNotFOundException(IdNotFoundException e){
        ExceptionHandlerDto exceptionHandlerDto = new ExceptionHandlerDto(e.getMessage(),404);
        return new ResponseEntity<>(exceptionHandlerDto, HttpStatus.NOT_FOUND);
    }
}
