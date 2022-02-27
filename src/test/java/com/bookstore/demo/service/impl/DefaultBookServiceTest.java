package com.bookstore.demo.service.impl;

import com.bookstore.demo.exception.BookNotFoundException;
import com.bookstore.demo.forms.BookForm;
import com.bookstore.demo.model.Book;
import com.bookstore.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for DefaultBookService
 */
public class DefaultBookServiceTest {
    private DefaultBookService service;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private Book savedBook;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new DefaultBookService(bookRepository);
    }

    @Test
    public void testAddBookWithNullBookForm() {
        assertThrows(NullPointerException.class, () -> service.addBook(null));
    }

    @Test
    public void testGetAllBooksNoBooksFound() {
        assertEquals(Collections.emptyList(), service.getAllBooks());
    }

    @Test
    public void testGetAllBooks() {
        given(bookRepository.findAll()).willReturn(List.of(savedBook));
        assertEquals(1, service.getAllBooks().size());
    }

    @Test
    public void testAddBookWith() {
        given(bookRepository.save(any(Book.class))).willReturn(savedBook);

        final BookForm bookForm = new BookForm();
        bookForm.setTitle("Title");
        bookForm.setIsbn("ISBN");
        service.addBook(bookForm);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testGetBookByIsdnWithNullISBN() {
        assertThrows(NullPointerException.class, () -> service.getBookByIsbn(null));
    }

    @Test
    public void testGetBookByIsdnNotFound() {
        given(bookRepository.findById("123")).willThrow(BookNotFoundException.class);
        assertThrows(BookNotFoundException.class, () -> Optional
                .ofNullable(service.getBookByIsbn("123"))
                .orElseThrow(() -> new BookNotFoundException("No book with given ISBN")));
    }

    @Test
    public void testGetBookByIsdnFound() {
        given(bookRepository.findById("123")).willReturn(Optional.of(savedBook));
        assertNotNull(service.getBookByIsbn("123"));
    }
}
