package com.lb.librarycatalogue.mapper.pojos;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CopiesOfBooksDto {


    private String isbnOfTitle;
    private String barcode;
    private LocalDate dueDate;
    private String title;
    private String memberBorrowingDocument;
}
