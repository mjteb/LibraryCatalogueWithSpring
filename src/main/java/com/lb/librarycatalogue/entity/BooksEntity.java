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

    @Column(name="total_number_of_copies")
    private int totalNumberOfCopies;

    @Column(name="number_of_copies_available")
    private int numberOfCopiesAvailable;

    @Column(name="number_of_reservations")
    private int numberOfReservations;

    @OneToMany(mappedBy = "isbnOfTitle", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CopiesOfBooksEntity> copiesOfBooks = new ArrayList<>();

    @OneToMany(mappedBy = "isbnOfReservedBook", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ReservedBooksEntity> reservations = new ArrayList<>();


}
