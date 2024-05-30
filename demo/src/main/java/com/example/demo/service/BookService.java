package com.example.demo.service;

import com.example.demo.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    List<Book> searchBooks(String keyword);
    void saveBook(Book book);
    void updateBook(Book book);
    void deleteBook(Long id);
}
