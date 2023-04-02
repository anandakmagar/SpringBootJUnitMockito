package com.testing.junits.service;

import com.testing.junits.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    public Book addBook(Book book);
    public Book getBookById(Long bookId);
    public List<Book> getAllBooks();
    public void deleteBookById(Long BookId);
    public Book updateBook(Book book);
}
