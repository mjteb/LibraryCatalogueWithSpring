package com.lb.librarycatalogue.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Data
@Table(name = "library_member")
@NoArgsConstructor
@AllArgsConstructor

public class LibraryMemberEntity {

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Id
    @Column(name="card_number")
    private String cardNumber;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name="membership_expiration_date")
    private LocalDate membershipExpirationDate;

    @Column(name="outstanding_late_fees")
    private double outstandingLateFees;

    @OneToMany(mappedBy = "memberBorrowingDocument", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CopiesOfBooksEntity> booksBorrowed = new ArrayList<>();

}
