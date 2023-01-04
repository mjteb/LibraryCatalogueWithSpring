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
@Table(name = "books_reserved")
@NoArgsConstructor
@AllArgsConstructor

public class ReservedBooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title_of_reserved_book")
    private String titleOfReservedBook;

    @JoinColumn(name = "id_member", referencedColumnName = "cardNumber")
    private String idMember;

    @JoinColumn(name = "isbn_of_reserved_book", referencedColumnName = "isbn")
    private String isbnOfReservedBook;

    @Column(name = "date_book_reserved")
    private LocalDate dateBookReserved;

    @Column(name = "position_in_line_for_book")
    private int positionInLineForBook;

}
