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
@Table(name = "copies_of_books")
@NoArgsConstructor
@AllArgsConstructor

public class CopiesOfBooksEntity {

    @JoinColumn(name = "isbn_of_title", referencedColumnName = "isbn")
    private String isbnOfTitle;

    @Id
    @JoinColumn(referencedColumnName = "cardNumber")
    private String barcode;

    @Column(name = "due_Date")
    private LocalDate dueDate;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;
}
