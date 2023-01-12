package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.repository.BooksBorrowedRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LibraryFeesServiceTest {

    @InjectMocks
    private LibraryFeesService libraryFeesService;

    @Mock
    private  BooksBorrowedRepository booksBorrowedRepository;

    @Mock
    private  LibraryMemberRepository libraryMemberRepository;


    @Test
    public void givenNothing_whenCheckIfBooksAreLate_thenItWorks() {
        //Arrange
        List<BooksBorrowed> booksBorrowedList = List.of(constructBorrowedBook());
        given(booksBorrowedRepository.findAll()).willReturn(booksBorrowedList);

        //Act
        libraryFeesService.checkIfBooksAreLate();

        //Assert
        verify(booksBorrowedRepository, times(1)).findAll();

    }

    @Test
    public void givenCardNumber_whenVerifyIfAnyBooksStillBorrowed_thenItWorks() {
        //Arrange

        LibraryMemberEntity member = constructLibraryMemberEntity();
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));
        member.setLibraryFeesFromBooksReturned(5.00);
        member.setLibraryFeesFromBooksCurrentlyBorrowed(15.00);

        //Act
       libraryFeesService.verifyIfAnyBooksStillBorrowed("SMITKAY19500401");

        //Assert
        verify(libraryMemberRepository, times(1)).findById("SMITKAY19500401");
        assertEquals(0.00, member.getLibraryFeesFromBooksCurrentlyBorrowed());
        assertEquals(5.00, member.getTotalLibraryFees());

    }


    @Test
    public void givenCardNumber_whenVerifyIfAnyBooksStillBorrowed_thenDoesNothing() {
        //Arrange

        LibraryMemberEntity member = constructLibraryMemberEntity();
        BooksBorrowed borrowedBook = constructBorrowedBook();
        member.getBooksBorrowedEntities().add(borrowedBook);
        member.setLibraryFeesFromBooksCurrentlyBorrowed(5.00);
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        //Act
        libraryFeesService.verifyIfAnyBooksStillBorrowed("SMITKAY19500401");

        //Assert
        verify(libraryMemberRepository, times(1)).findById("SMITKAY19500401");
        assertEquals(5.00, member.getLibraryFeesFromBooksCurrentlyBorrowed());

    }

    @Test
    public void givenCardNumber_whenCalculateLateFeesForBooksCurrentlyBorrowed_thenWorks() {
        //Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));
        member.setLibraryFeesFromBooksReturned(5.00);
        member.setLibraryFeesFromBooksCurrentlyBorrowed(5.00);

        //Act
        libraryFeesService.calculateLateFeesForBooksCurrentlyBorrowed("SMITKAY19500401");

        //Assert
        verify(libraryMemberRepository, times(1)).findById("SMITKAY19500401");
        assertEquals(10.00, member.getTotalLibraryFees());
    }

    @Test
    public void givenDueDateAndLibraryMember_whenCalculateLateFeesPerEachBookCurrentlyBorrowed_thenWorks() {
        //Arrange
        LocalDate dueDate = LocalDate.now().minusWeeks(1);
        LibraryMemberEntity member = constructLibraryMemberEntity();
        double expectedLateFees = 7 * 0.25;

        //Act
        libraryFeesService.calculateLateFeesPerEachBookCurrentlyBorrowed(dueDate, member);

        //Assert
        assertEquals(expectedLateFees, member.getLibraryFeesFromBooksCurrentlyBorrowed());
    }


    @Test
    public void givenBooksBorrowed_whenCheckIfBooksReturnedLate_thenWorks() {
        //Arrange
        BooksBorrowed borrowedBook = constructBorrowedBook();
        LibraryMemberEntity member = constructLibraryMemberEntity();
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        //Act
        libraryFeesService.checkIfBooksReturnedLate(borrowedBook);

        //Assert
        verify(libraryMemberRepository, times(1)).findById(borrowedBook.getIdMember());
    }

    @Test
    public void givenDueDateAndLibraryMember_whenCalculateLateFeesForBookBeingReturned_thenWorks() {
        //Arrange
        LocalDate dueDate = LocalDate.now().minusWeeks(1);
        LibraryMemberEntity member = constructLibraryMemberEntity();
        member.setLibraryFeesFromBooksReturned(10);
        double expectedLateFees = (7 * 0.25) + 10;

        //Act
        libraryFeesService.calculateLateFeesForBookBeingReturned(dueDate, member);

        //Assert
        assertEquals(expectedLateFees, member.getLibraryFeesFromBooksReturned());
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