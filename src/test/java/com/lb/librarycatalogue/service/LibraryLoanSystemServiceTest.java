package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.*;
import com.lb.librarycatalogue.repository.*;
import com.lb.librarycatalogue.utils.LibraryMemberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LibraryLoanSystemServiceTest {

    @InjectMocks
    private LibraryLoanSystemService libraryLoanSystemService;

    @Mock
    private  LibraryMemberRepository libraryMemberRepository;

    @Mock
    private  LibraryLoanSystemRepository libraryLoanSystemRepository;

    @Mock
    private  BooksRepository booksRepository;

    @Mock
    private  ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;

    @Mock
    private  BookReservationService bookReservationService;

    @Mock
    private  CopiesOfBooksService copiesOfBooksService;

    @Mock
    private  LibraryFeesService libraryFeesService;

    @Mock
    private  BooksBorrowedRepository booksBorrowedRepository;

    @Mock
    LibraryMemberUtils libraryMemberUtils;



    @Test
    public void givenBookBorrowed_whenBorrowBook_thenItWorks() {
        //Arrange
        BooksBorrowed bookBorrowed = constructBorrowedBook();
        BooksEntity book = constructBooksEntity();
        given(LibraryMemberUtils.verifyMemberCanBorrowBook(libraryMemberRepository, bookBorrowed)).willCallRealMethod();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(book));

        //Act
        libraryLoanSystemService.borrowBook(bookBorrowed);

        //Assert
        verify(libraryLoanSystemRepository, times(1)).saveAndFlush(bookBorrowed);
        verify(booksRepository, times(1)).findById("9781984822178");
    }

    private BooksBorrowed constructBorrowedBook() {
        return BooksBorrowed.builder()
                .id(1)
                .idBook("af7ee5d2-d278-4e8d-bc05-c5481af3d837")
                .idMember("SMITJON19500401")
                .title("Normal People")
                .isbnOfBorrowedBook("9781984822178")
                .dateBookBorrowed(null)
                .dueDate(null)
                .build();
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