package com.eShelf.info.e.library.exception;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException() {
    }

    public IdNotFoundException(String message) {
        super(message);
    }

}
