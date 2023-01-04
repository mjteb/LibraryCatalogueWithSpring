package com.lb.librarycatalogue.mapper.pojos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowedBooksDto {

    private int id;
    private String idMember;
    private String idBook;
    private LocalDate dateBookBorrowed;
    private LocalDate dueDate;
    private String isbnOfBorrowedBook;
    private String title;

}
