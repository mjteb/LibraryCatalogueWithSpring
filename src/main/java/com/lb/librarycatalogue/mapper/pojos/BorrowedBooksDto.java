package com.lb.librarycatalogue.mapper.pojos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BorrowedBooksDto {

    private int id;
    private String idMember;
    private String idBook;
    private LocalDate dateBookBorrowed;
    private LocalDate dueDate;
    private String isbnOfBorrowedBook;
    private String title;

}
