package com.testing.junits.controller;

import com.testing.junits.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.testing.junits.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId){
//        Book book = bookService.getBookById(bookId);
//        return ResponseEntity.ok(book);
//    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable("id") Long bookId){
        bookService.deleteBookById(bookId);
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") Long bookId){
        return bookService.getBookById(bookId);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PutMapping("/update")
    public Book updateBook(@RequestBody Book book){
        return bookService.updateBook(book);

    }
}
