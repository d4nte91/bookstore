package com.bookstore.demo.controller;

import com.bookstore.demo.exception.BookNotFoundException;
import com.bookstore.demo.forms.BookForm;
import com.bookstore.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;

@Controller
public class BookController implements WebMvcConfigurer {
    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
    }

    @Autowired
    private BookService bookService;

    @GetMapping("/index")
    public String getAllBooks(final Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/getBook/{isbn}")
    public String getBook(@PathVariable final String isbn, final Model model) {
        try {
            model.addAttribute("book", bookService.getBookByIsbn(isbn));
        } catch (final BookNotFoundException e) {
            LOG.error("Couldn't find book by given ISBN {}", isbn);
        }
        return "bookInfo";
    }

    @GetMapping("/addBook")
    public String showForm(final BookForm bookForm) {
        return "addBookForm";
    }

    @PostMapping("/addBook")
    public String addBook(@Valid final BookForm bookForm, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addBookForm";
        }
        bookService.addBook(bookForm);
        return "redirect:/index";
    }
}