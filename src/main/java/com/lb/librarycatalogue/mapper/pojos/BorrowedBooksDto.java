package com.lb.librarycatalogue.mapper.pojos;

import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
