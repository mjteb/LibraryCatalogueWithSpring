package com.lb.librarycatalogue.mapper.pojos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservedBooksDto {


    private int id;
    private String titleOfReservedBook;
    private String idMember;
    private String isbnOfReservedBook;
    private LocalDate dateBookReserved;
    private int positionInLineForBook;
}
