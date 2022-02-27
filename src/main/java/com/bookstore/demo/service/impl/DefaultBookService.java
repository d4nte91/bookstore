package com.bookstore.demo.service.impl;

import com.bookstore.demo.exception.BookNotFoundException;
import com.bookstore.demo.forms.BookForm;
import com.bookstore.demo.model.Author;
import com.bookstore.demo.model.Book;
import com.bookstore.demo.repository.BookRepository;
import com.bookstore.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DefaultBookService implements BookService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultBookService.class);

    private final BookRepository bookRepository;

    public DefaultBookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        LOG.debug("Called getAllBooks");
        return bookRepository.findAll();
    }

    @Override
    public Book getBookByIsbn(final String isbn) throws BookNotFoundException {
        Objects.requireNonNull(isbn);
        LOG.debug("Called getBookByIsbn with isbn: {}", isbn);
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("No book with given ISBN"));
    }

    @Override
    public Book addBook(final BookForm bookForm) {
        Objects.requireNonNull(bookForm);

        final Author author = new Author(bookForm.getFirstName(), bookForm.getLastName());
        final Book book = new Book(bookForm.getIsbn(), bookForm.getTitle(), author);
        LOG.debug("Called addBook with isbn: {} and title: {}", book.getIsbn(), book.getTitle());
        final Book savedBook = bookRepository.save(book);
        LOG.info("Book with isbn: {} and title: {} was successfully saved", savedBook.getIsbn(), savedBook.getTitle());
        return savedBook;
    }
}
