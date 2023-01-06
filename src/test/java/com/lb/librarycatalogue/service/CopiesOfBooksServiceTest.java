package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.*;
import com.lb.librarycatalogue.repository.CopiesOfBooksRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
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
class CopiesOfBooksServiceTest {

    @InjectMocks
    private CopiesOfBooksService copiesOfBooksService;

    @Mock
    private CopiesOfBooksRepository copiesOfBooksRepository;

    @Mock
    private ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;


    @Test
    public void givenBarcode_whenChangeCopyStatusToOnLoanAndAddDueDate_thenItWorks() {
        //Arrange
        CopiesOfBooksEntity copy = constructCopyOfBookEntity();
        String status = "ON LOAN";
        LocalDate dueDate = LocalDate.now().plusWeeks(3);
        given(copiesOfBooksRepository.findById(anyString())).willReturn(Optional.of(copy));

        //Act
        copiesOfBooksService.changeCopyStatusToOnLoanAndAddDueDate("af7ee5d2-d278-4e8d-bc05-c5481af3d837");


        //Assert
        assertEquals(status, copy.getStatus());
        assertEquals(dueDate, copy.getDueDate());
        verify(copiesOfBooksRepository, times(1)).findById("af7ee5d2-d278-4e8d-bc05-c5481af3d837");
    }

    @Test
    public void givenBarcode_whenChangeCopyStatusToOnLoanAndAddDueDate_thenItThrowsError() {
        //Arrange
        given(copiesOfBooksRepository.findById(anyString())).willReturn(Optional.empty());

        //Act and Assert
        assertThrows(RuntimeException.class, () -> copiesOfBooksService.changeCopyStatusToOnLoanAndAddDueDate("af7ee5d2-d278-4e8d-bc05-c5481af3d837"));
    }

    @Test
    public void givenBarcode_whenChangeCopyStatusToAvailableAndRemoveDueDate_thenItWorks() {
        //Arrange
        CopiesOfBooksEntity copy = constructCopyOfBookEntity();
        String status = "AVAILABLE";
        LocalDate dueDate = null;
        given(copiesOfBooksRepository.findById(anyString())).willReturn(Optional.of(copy));

        //Act
        copiesOfBooksService.changeCopyStatusToAvailableAndRemoveDueDate("af7ee5d2-d278-4e8d-bc05-c5481af3d837");


        //Assert
        assertEquals(status, copy.getStatus());
        assertEquals(dueDate, copy.getDueDate());
        verify(copiesOfBooksRepository, times(1)).findById("af7ee5d2-d278-4e8d-bc05-c5481af3d837");
    }


    @Test
    public void givenBarcode_whenUpdateCopyAvailableForPickUp_thenItWorks() {
        //Arrange
        CopiesOfBooksEntity copy = constructCopyOfBookEntity();
        String status = "ON HOLD FOR PICK UP";
        LocalDate dueDate = null;
        given(copiesOfBooksRepository.findById(anyString())).willReturn(Optional.of(copy));

        //Act
        copiesOfBooksService.updateCopyAvailableForPickUp("af7ee5d2-d278-4e8d-bc05-c5481af3d837");


        //Assert
        assertEquals(status, copy.getStatus());
        assertEquals(dueDate, copy.getDueDate());
        verify(copiesOfBooksRepository, times(1)).findById("af7ee5d2-d278-4e8d-bc05-c5481af3d837");
    }

    @Test
    public void givenBarcode_whenDeleteCopy_thenItWorks() {
        //Arrange
        CopiesOfBooksEntity copy = constructCopyOfBookEntity();
        given(copiesOfBooksRepository.findById(anyString())).willReturn(Optional.of(copy));

        //Act
        copiesOfBooksService.deleteCopy("af7ee5d2-d278-4e8d-bc05-c5481af3d837");


        //Assert
        verify(copiesOfBooksRepository, times(1)).delete(copy);
    }

    @Test
    public void givenBooksBorrowedEntityAndRepo_whenVerifyCopyIsAvailableForLoan_thenItWorks() {
        //Arrange
        CopiesOfBooksEntity copy = constructCopyOfBookEntity();
        BooksBorrowed bookBorrowed = constructBorrowedBook();
        List<ReservationsAvailableToBorrowEntity> reservation = List.of(constructReservationAvailableToBorrow());
        given(copiesOfBooksRepository.findById(anyString())).willReturn(Optional.of(copy));
        given(reservationsAvailableForPickUpRepository.findAll()).willReturn(reservation);

        //Act
        copiesOfBooksService.verifyCopyIsAvailableForLoan(bookBorrowed, reservationsAvailableForPickUpRepository);


        //Assert
        verify(copiesOfBooksRepository, times(1)).findById("af7ee5d2-d278-4e8d-bc05-c5481af3d837");
        verify(reservationsAvailableForPickUpRepository, times(1)).deleteById(5);
    }

    @Test
    public void givenBooksBorrowedEntityAndRepo_whenVerifyCopyIsAvailableForLoan_thenThrowsError() {
        //Arrange
        CopiesOfBooksEntity copy = constructCopyOfBookEntity();
        BooksBorrowed bookBorrowed = constructBorrowedBook();
        copy.setStatus("ON LOAN");
        List<ReservationsAvailableToBorrowEntity> reservation = new ArrayList<>();
        given(copiesOfBooksRepository.findById(anyString())).willReturn(Optional.of(copy));
        given(reservationsAvailableForPickUpRepository.findAll()).willReturn(reservation);


        //Act and Assert
        assertThrows(RuntimeException.class, () -> copiesOfBooksService.verifyCopyIsAvailableForLoan(bookBorrowed, reservationsAvailableForPickUpRepository));
    }

    private CopiesOfBooksEntity constructCopyOfBookEntity() {
        return CopiesOfBooksEntity.builder()
                .isbnOfTitle("9781984822178")
                .title("Normal People")
                .barcode("af7ee5d2-d278-4e8d-bc05-c5481af3d837")
                .dueDate(LocalDate.now())
                .status("AVAILABLE")
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

    private ReservationsAvailableToBorrowEntity constructReservationAvailableToBorrow() {
        return ReservationsAvailableToBorrowEntity.builder()
                .id(5)
                .idMember("SMITJON19500401")
                .titleOfReservedBook("Normal People")
                .isbnOfReservedBook("9781984822178")
                .barcodeOfReservedBook("af7ee5d2-d278-4e8d-bc05-c5481af3d837")
                .dateBookAvailableToBorrow(LocalDate.now())
                .deadlineDateToBorrowBook(LocalDate.now().plusWeeks(1))
                .build();
    }
}