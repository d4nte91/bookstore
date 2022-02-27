package com.bookstore.demo.service;

import com.bookstore.demo.exception.BookNotFoundException;
import com.bookstore.demo.forms.BookForm;
import com.bookstore.demo.model.Book;

import java.util.List;

public interface BookService {

    /**
     * Method that returns all books
     *
     * @return set of books
     */
    List<Book> getAllBooks();

    /**
     * Method that returns a book with given <code>isbn</code>
     *
     * @param isbn unique parameter of a book
     * @return book with given isbn
     */
    Book getBookByIsbn(String isbn) throws BookNotFoundException;

    /**
     * Method that saves a book to db
     *
     * @param bookForm form with book related attributes
     * @return saved book in db
     */
    Book addBook(BookForm bookForm);
}
