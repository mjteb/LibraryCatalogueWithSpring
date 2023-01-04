package com.lb.librarycatalogue.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@Table(name = "library_member")
@NoArgsConstructor
@AllArgsConstructor

public class LibraryMemberEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Id
    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "membership_expiration_date")
    private LocalDate membershipExpirationDate;

    @Column(name = "library_fees_from_books_currently_borrowed")
    private double libraryFeesFromBooksCurrentlyBorrowed;

    @Column(name = "library_fees_from_returned_books")
    private double libraryFeesFromBooksReturned;

    @Column(name = "total_library_fees")
    private double totalLibraryFees;

    @Column(name = "number_of_books_borrowed")
    private int numberOfBooksBorrowed;

    @Column(name = "number_of_books_reserved")
    private int numberOfBooksReserved;


    @OneToMany(mappedBy = "idMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<BooksBorrowed> booksBorrowedEntities = new ArrayList<>();

    @OneToMany(mappedBy = "idMember", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private List<ReservedBooksEntity> booksReserved = new ArrayList<>();

    @OneToMany(mappedBy = "idMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ReservationsAvailableToBorrowEntity> reservationsAvailableToBorrow = new ArrayList<>();

}

