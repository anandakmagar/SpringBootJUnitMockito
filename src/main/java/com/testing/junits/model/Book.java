package com.testing.junits.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="book_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    @NonNull
    private String name;
    @NonNull
    private String summary;
    private int rating;

//    public Book() {
//    }
//
//    public Book(Long bookId, @NonNull String name, @NonNull String summary, int rating) {
//        this.bookId = bookId;
//        this.name = name;
//        this.summary = summary;
//        this.rating = rating;
//    }
//
//    public void setBookId(Long bookId) {
//        this.bookId = bookId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setSummary(String summary) {
//        this.summary = summary;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public Long getBookId() {
//        return bookId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getSummary() {
//        return summary;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    @Override
//    public String toString() {
//        return "Book{" +
//                "bookId=" + bookId +
//                ", name='" + name + '\'' +
//                ", summary='" + summary + '\'' +
//                ", rating=" + rating +
//                '}';
//    }
}
