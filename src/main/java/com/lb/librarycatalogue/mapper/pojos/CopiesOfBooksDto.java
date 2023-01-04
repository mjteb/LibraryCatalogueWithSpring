package com.lb.librarycatalogue.mapper.pojos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CopiesOfBooksDto {


    private String isbnOfTitle;
    private String barcode;
    private LocalDate dueDate;
    private String title;
    private String memberBorrowingDocument;
}
