package com.lb.librarycatalogue.mapper.pojos;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class LibraryMemberDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String cardNumber;
    private LocalDate dateOfBirth;
    private LocalDate membershipExpirationDate;
    private double outstandingLateFees;
    private List<BooksBorrowed> booksBorrowedEntities = new ArrayList<>();
}
