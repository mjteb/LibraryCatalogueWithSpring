package com.lb.librarycatalogue.mapper.pojos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservedBooksDto {


    private int id;
    private String titleOfReservedBook;
    private String idMember;
    private String isbnOfReservedBook;
    private LocalDate dateBookReserved;
    private int positionInLineForBook;
}
