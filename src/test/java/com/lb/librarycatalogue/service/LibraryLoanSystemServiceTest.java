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
import static org.mockito.ArgumentMatchers.anyInt;
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




    @Test
    public void givenBookBorrowed_whenBorrowBook_thenItWorks() {
        //Arrange
        BooksBorrowed bookBorrowed = constructBorrowedBook();
        BooksEntity book = constructBooksEntity();
        LibraryMemberEntity member = constructLibraryMemberEntity();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(book));
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));


        //Act
        libraryLoanSystemService.borrowBook(bookBorrowed);

        //Assert
        verify(libraryLoanSystemRepository, times(1)).saveAndFlush(bookBorrowed);
        verify(booksRepository, times(1)).findById("9781984822178");
    }

    @Test
    public void givenBookBorrowedId_whenReturnBook_thenItWorks() {
        //Arrange
        BooksBorrowed bookBorrowed = constructBorrowedBook();
        LibraryMemberEntity member = constructLibraryMemberEntity();
        given(booksBorrowedRepository.findById(anyInt())).willReturn(Optional.of(bookBorrowed));
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));


        //Act
        libraryLoanSystemService.returnBook(5);

        //Assert
        verify(booksBorrowedRepository, times(1)).findById(5);

    }

    private BooksBorrowed constructBorrowedBook() {
        return BooksBorrowed.builder()
                .idBook("af7ee5d2-d278-4e8d-bc05-c5481af3d837")
                .idMember("SMITJON19500401")
                .title("Normal People")
                .isbnOfBorrowedBook("9781984822178")
                .dateBookBorrowed(LocalDate.now())
                .dueDate(LocalDate.now().plusWeeks(3))

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

    private LibraryMemberEntity constructLibraryMemberEntity() {
        return LibraryMemberEntity.builder()
                .firstName("Kayla")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(2015, 12, 31))
                .membershipExpirationDate(LocalDate.now().plusYears(2))
                .phoneNumber("1231235454")
                .cardNumber("SMITKAY19500401")
                .build();
    }

}