package com.bookstore.demo.exception;

/**
 * Bookstore related exception class
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(final String message) {
        super(message);
    }
}
