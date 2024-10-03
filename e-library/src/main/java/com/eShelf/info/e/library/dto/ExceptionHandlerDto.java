package com.eShelf.info.e.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionHandlerDto {
    private String message;
    private  int code;

    public ExceptionHandlerDto() {
    }

    public ExceptionHandlerDto(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
