package com.bookstore.demo.controller;

import com.bookstore.demo.exception.BookNotFoundException;
import com.bookstore.demo.forms.BookForm;
import com.bookstore.demo.model.Book;
import com.bookstore.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class RestBookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getAllBooks", produces = "application/json")
    @GetMapping
    public List<Book> getAllBooks() {
        final List<Book> books = bookService.getAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            throw new BookNotFoundException("Books not found");
        }
        return books;
    }

    @RequestMapping(value = "/addBook", produces = "application/json", consumes = "application/json")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody @Valid final BookForm bookForm) {
        return bookService.addBook(bookForm);
    }

}
