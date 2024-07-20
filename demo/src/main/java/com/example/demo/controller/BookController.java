package com.example.demo.controller;

import com.example.demo.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String viewHomePage(Model model) {
        model.addAttribute("listBooks", bookService.getAllBooks());
        return "index"; // Thymeleaf template name
    }

    @PostMapping("/saveBook")
    public String saveBook(@RequestParam String name, @RequestParam String author, @RequestParam String description) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
