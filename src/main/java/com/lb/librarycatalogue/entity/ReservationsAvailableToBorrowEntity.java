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
@Table(name = "reservations_available_to_borrow")
@NoArgsConstructor
@AllArgsConstructor

public class ReservationsAvailableToBorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title_of_reserved_book")
    private String titleOfReservedBook;

    @JoinColumn(name = "id_member", referencedColumnName = "cardNumber")
    private String idMember;

    @JoinColumn(name = "isbn_of_reserved_book", referencedColumnName = "isbn")
    private String isbnOfReservedBook;

    @Column(name = "date_book_available_to_borrow")
    private LocalDate dateBookAvailableToBorrow;

    @Column(name = "deadline_date_to_borrow_book")
    private LocalDate deadlineDateToBorrowBook;


    @JoinColumn(name = "barcode_of_reserved_book", referencedColumnName = "barcode")
    private String barcodeOfReservedBook;
}
