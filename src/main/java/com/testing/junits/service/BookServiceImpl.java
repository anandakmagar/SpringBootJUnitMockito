package com.testing.junits.service;

import com.testing.junits.exception.ResourceNotFoundException;
import com.testing.junits.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testing.junits.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

//    @Override
//    public Book getBookById(Long bookId){
//        return bookRepository.findById(bookId).get();
//    }
//
    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).
               orElseThrow(() -> new ResourceNotFoundException("Book not exist with id" + bookId));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book updateBook(Book book) throws ResourceNotFoundException {
        if (book == null || book.getBookId() == null){
            throw new ResourceNotFoundException("Book or book id must not be null");
        }

        Optional<Book> optionalBook = bookRepository.findById(book.getBookId());
        if (!optionalBook.isPresent()){
            throw new ResourceNotFoundException("Book with id: " + book.getBookId() + " does not exist");
        }

        Book existingBook = optionalBook.get();
        existingBook.setName(book.getName());
        existingBook.setSummary(book.getSummary());
        existingBook.setRating(book.getRating());

        return bookRepository.save(existingBook);
    }
}