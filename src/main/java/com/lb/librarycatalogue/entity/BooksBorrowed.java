package com.lb.librarycatalogue.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Entity
@Data
@Table(name = "books_borrowed")
@NoArgsConstructor
@AllArgsConstructor

public class BooksBorrowed {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name="id_member", referencedColumnName = "cardNumber")
    private String idMember;

    @JoinColumn(name="id_book", referencedColumnName = "barcode")
    private String idBook;

    @Column(name="date_book_borrowed")
    private LocalDate dateBookBorrowed;

    @Column(name="due_date")
    private LocalDate dueDate;

    @Column(name="title")
    private String title;

    @JoinColumn(name ="isbn_of_borrowed_book", referencedColumnName = "isbn")
    private String isbnOfBorrowedBook;

}
