package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.mapper.pojos.BooksDto;
import com.lb.librarycatalogue.repository.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceTestForSpecifications {

    @Autowired
//    @InjectMocks
    private BooksService booksService;

    @Autowired
    private BooksRepository booksRepository;

    @BeforeEach
    public void init() {
        List<CopiesOfBooksEntity> copies = new ArrayList<>();
        List<ReservedBooksEntity> reservations = new ArrayList<>();
        booksRepository.save(new BooksEntity("The Idiot", "Elif Batuman","9781594205613", 1, 1, 0, copies, reservations));
        booksRepository.save(new BooksEntity("The Idiot", "Fyodor Dostoyevsky","9780226159621", 1, 1, 0, copies, reservations));
        booksRepository.save(new BooksEntity("Normal People", "Sally Rooney","9781984822178", 1, 1, 0, copies, reservations));


    }

    @Test
    public void givenNothing_whenGetBook_thenReturnsAllBooks() {
        //Arrange

        //Act
       List<BooksEntity> allBooks = booksService.getBook(null, null, null);

        // Assert
        verify(booksRepository, times(1)).findAll();
        assertEquals(3, allBooks.size());
    }

    private BooksEntity constructBooksEntity() {
        List<CopiesOfBooksEntity> copies = new ArrayList<>();
        List<ReservedBooksEntity> reservations = new ArrayList<>();
        return BooksEntity.builder()
                .author("Sally Rooney")
                .isbn("9781984822178")
                .title("Normal People")
                .totalNumberOfCopies(0)
                .numberOfCopiesAvailable(0)
                .numberOfReservations(0)
                .copiesOfBooks(copies)
                .reservations(reservations)
                .build();
    }

}