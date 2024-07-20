package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods
    List<Book> findByName(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByNameAndAuthor(String name, String author);
    List<Book> findByNameContaining(String name);
    List<Book> findByAuthorContaining(String author);
    List<Book> findByNameContainingOrAuthorContaining(String name, String author);
    List<Book> findByIdBetween(Long startId, Long endId);
    List<Book> findByNameOrderByAuthorAsc(String name);
    List<Book> findByNameOrderByAuthorDesc(String name);
    Long countByAuthor(String author);
    void deleteByName(String name);
    void deleteByAuthor(String author);
    void deleteByNameAndAuthor(String name, String author);
    void deleteById(Long id);

    // Custom JPQL query
    @Query("SELECT b FROM Book b WHERE b.name LIKE %:keyword% OR b.author LIKE %:keyword%")
    List<Book> searchBooks(@Param("keyword") String keyword);
}
