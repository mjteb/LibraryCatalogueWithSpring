package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.*;
import com.lb.librarycatalogue.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class BookReservationServiceTest {

    @InjectMocks
    private BookReservationService bookReservationService;

    @Mock
    private BookReservationRepository bookReservationRepository;
    @Mock
    private BooksRepository booksRepository;
    @Mock
    private LibraryMemberRepository libraryMemberRepository;
    @Mock
    private ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;
    @Mock
    private ReservationsAvailableForPickUpService reservationsAvailableForPickUpService;
    @Mock
    private CopiesOfBooksService copiesOfBooksService;
    @Mock
    private CopiesOfBooksRepository copiesOfBooksRepository;



    @Test
    public void givenCardNumberBarcodeBook_whenAddBookToReservationsAvailableForPickUp_ThenWorks() {
        //Arrange
        BooksEntity bookToUpdate = constructBooksEntity();
        ReservationsAvailableToBorrowEntity reservationsAvailableToBorrow = constructReservationAvailableToBorrow();

        //Act
        bookReservationService.addBookToReservationsAvailableForPickUp("SMITKAY19500401", "af7ee5d2-d278-4e8d-bc05-c5481af3d837", bookToUpdate);

        //Assert
        verify(reservationsAvailableForPickUpRepository, times(1)).save(reservationsAvailableToBorrow);
        verify(reservationsAvailableForPickUpRepository, times(1)).flush();
    }


    @Test
    public void givenReservedBook_whenVerificationsForRequestToReserveBook_ThenWorks() {
        //Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();
        BooksEntity bookToUpdate = constructBooksEntity();
        ReservedBooksEntity reservedBook = constructReservedBooks();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(bookToUpdate));
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        //Act
        bookReservationService.verificationsForRequestToReserveBook(reservedBook);

        //Assert
        verify(booksRepository, times(3)).findById("9781984822178");
    }

//new
    @Test
    public void givenReservedBook_whenStepsToPutBookAsideThatIsAvailable_ThenWorks() {
        //Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();
        BooksEntity bookToUpdate = constructBooksEntity();
        ReservedBooksEntity reservedBook = constructReservedBooks();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(bookToUpdate));
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        //Act
        bookReservationService.verificationsForRequestToReserveBook(reservedBook);

        //Assert
        verify(booksRepository, times(3)).findById("9781984822178");
    }


    @Test
    public void givenBookRepoBorrowedBookAndReservationAvailableRepo_whenCheckIfBookReserved_ThenWorksInCaseWithNoReservations() {
        //Arrange
        BooksEntity bookToUpdate = constructBooksEntity();
        BooksBorrowed borrowedBook = constructBorrowedBook();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(bookToUpdate));

        //Act
        bookReservationService.checkIfBookReserved(booksRepository, borrowedBook, reservationsAvailableForPickUpRepository);

        //Assert
        verify(booksRepository, times(2)).findById("9781984822178");
    }

    @Test
    public void givenBookRepoBorrowedBookAndReservationAvailableRepo_whenCheckIfBookReserved_ThenWorksInCaseWithReservations() {
        //Arrange
        BooksEntity bookToUpdate = constructBooksEntity();
        BooksBorrowed borrowedBook = constructBorrowedBook();
      bookToUpdate.getReservations().add(constructReservedBooks());
        given(booksRepository.findById(anyString())).willReturn(Optional.of(bookToUpdate));

        //Act
        bookReservationService.checkIfBookReserved(booksRepository, borrowedBook, reservationsAvailableForPickUpRepository);

        //Assert
        verify(booksRepository, times(2)).findById("9781984822178");
    }



    @Test
    public void givenId_whenStepForDeletingReservation_ThenWorks() {
        //Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();
        BooksEntity bookToUpdate = constructBooksEntity();
        ReservedBooksEntity reservedBook = constructReservedBooks();
        ReservationsAvailableToBorrowEntity reservationsAvailableToBorrow = constructReservationAvailableToBorrow();
        given(bookReservationRepository.findById(anyInt())).willReturn(Optional.of(reservedBook));
        given(booksRepository.findById(anyString())).willReturn(Optional.of(bookToUpdate));
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        //Act
        bookReservationService.stepForDeletingReservation(1);

        //Assert
        verify(bookReservationRepository, times(2)).findById(1);
        verify(booksRepository, times(1)).findById("9781984822178");
    }

    @Test
    public void givenNothing_whenVerifyIfReservationNotPickedUpOnTime_ThenWorks() {
        //Arrange
        BooksEntity bookToUpdate = constructBooksEntity();
        ReservationsAvailableToBorrowEntity reservationsAvailableToBorrow = constructReservationAvailableToBorrow();

        //Act
        bookReservationService.addBookToReservationsAvailableForPickUp("SMITKAY19500401", "af7ee5d2-d278-4e8d-bc05-c5481af3d837", bookToUpdate);

        //Assert
        verify(reservationsAvailableForPickUpRepository, times(1)).save(reservationsAvailableToBorrow);
        verify(reservationsAvailableForPickUpRepository, times(1)).flush();
    }

    @Test
    public void givenBookEntity_whenUpdateNumberOfReservations_ThenWorks() {
        //Arrange
        BooksEntity bookToUpdate = constructBooksEntity();
        List<ReservedBooksEntity> reservedBooksList = List.of(constructReservedBooks());
        given(bookReservationRepository.findAll()).willReturn(reservedBooksList);

        //Act
        bookReservationService.updateNumberOfReservations(bookToUpdate);

        //Assert
        verify(bookReservationRepository, times(1)).findAll();
        assertEquals(1, bookToUpdate.getNumberOfReservations());
    }

    //New
    @Test
    public void givenReservationAvailableForPickUpServiceAndReservationAvailableToBorrowEntity_whenUpdatesIfReservationNotPickedUp_ThenWorksWithNoReservations() {
        //Arrange
        BooksEntity bookToUpdate = constructBooksEntity();
        ReservationsAvailableToBorrowEntity reservation = constructReservationAvailableToBorrow();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(bookToUpdate));

        //Act
        bookReservationService.updatesIfReservationNotPickedUp(reservationsAvailableForPickUpService, reservation);

        //Assert
        verify(booksRepository, times(1)).findById("9781984822178");
    }

    @Test
    public void givenReservationAvailableForPickUpServiceAndReservationAvailableToBorrowEntity_whenUpdatesIfReservationNotPickedUp_ThenWorksWithReservation() {
        //Arrange
        BooksEntity bookToUpdate = constructBooksEntity();
        LibraryMemberEntity member = constructLibraryMemberEntity();
        bookToUpdate.setNumberOfReservations(1);
        bookToUpdate.getReservations().add(constructReservedBooks());
        ReservationsAvailableToBorrowEntity reservation = constructReservationAvailableToBorrow();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(bookToUpdate));
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        //Act
        bookReservationService.updatesIfReservationNotPickedUp(reservationsAvailableForPickUpService, reservation);

        //Assert
        verify(booksRepository, times(1)).findById("9781984822178");
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

    private ReservedBooksEntity constructReservedBooks() {
        return ReservedBooksEntity.builder()
                .idMember("SMITKAY19500401")
                .titleOfReservedBook("Normal People")
                .isbnOfReservedBook("9781984822178")
                .positionInLineForBook(1)
                .dateBookReserved(LocalDate.now())
                .build();
    }


    private ReservationsAvailableToBorrowEntity constructReservationAvailableToBorrow() {
        return ReservationsAvailableToBorrowEntity.builder()
                .idMember("SMITKAY19500401")
                .titleOfReservedBook("Normal People")
                .isbnOfReservedBook("9781984822178")
                .barcodeOfReservedBook("af7ee5d2-d278-4e8d-bc05-c5481af3d837")
                .dateBookAvailableToBorrow(LocalDate.now())
                .deadlineDateToBorrowBook(LocalDate.now().plusWeeks(1))
                .build();
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

}