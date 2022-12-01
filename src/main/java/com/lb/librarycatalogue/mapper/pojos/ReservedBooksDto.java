package com.lb.librarycatalogue.mapper.pojos;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservedBooksDto {


    private int id;
    private String titleOfReservedBook;
    private String idMember;
    private String isbnOfReservedBook;
    private LocalDate dateBookReserved;
    private int positionInLineForBook;
}
