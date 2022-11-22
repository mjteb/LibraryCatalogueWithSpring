package com.lb.librarycatalogue.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor

public class BooksEntity {

    private String title;
    private String author;
    @Id
    private String isbn;

    @OneToMany(mappedBy = "isbnOfTitle", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CopiesOfBooksEntity> copiesOfBooks = new ArrayList<>();

}
