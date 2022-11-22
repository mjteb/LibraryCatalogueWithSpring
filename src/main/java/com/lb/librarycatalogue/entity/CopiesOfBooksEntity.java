package com.lb.librarycatalogue.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name = "copies_of_books")
@NoArgsConstructor
@AllArgsConstructor

public class CopiesOfBooksEntity {

    @ManyToOne()
    @JoinColumn(name = "isbn_of_title", referencedColumnName = "isbn")
    private BooksEntity isbnOfTitle;

    @Id
    private String barcode;

    @Column(name="due_Date")
    private LocalDate dueDate;

    @JoinColumn(name = "member_borrowing_document", referencedColumnName = "card_number")
    private String memberBorrowingDocument;
}
