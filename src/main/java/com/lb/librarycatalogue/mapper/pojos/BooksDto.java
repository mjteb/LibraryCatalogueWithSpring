package com.lb.librarycatalogue.mapper.pojos;

import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
public class BooksDto {


    private String title;
    private String author;
    private String isbn;
    private List<CopiesOfBooksEntity> copiesOfBooks = new ArrayList<>();
    private int totalNumberOfCopies;
    private int numberOfCopiesAvailable;
    private int numberOfReservations;
}
