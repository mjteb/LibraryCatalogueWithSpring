package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationsAvailableForPickUpServiceTest {

    @InjectMocks
    private ReservationsAvailableForPickUpService reservationsAvailableForPickUpService;

    @Mock
    private ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;

    @Test
    public void givenNothing_getListsOfReservationPickedUp_thenItWorks() {
        //Arrange
        List<ReservationsAvailableToBorrowEntity> reservation = List.of(constructReservationAvailableToBorrow());
        given(reservationsAvailableForPickUpRepository.findAll()).willReturn(reservation);

        //Act
        List<ReservationsAvailableToBorrowEntity> response = reservationsAvailableForPickUpService.getListsOfReservationPickedUp();

        //Assert
        Assertions.assertThat(response).isNotNull();
        verify(reservationsAvailableForPickUpRepository, times(1)).findAll();
    }

    @Test
    public void givenReservationAvailableToBorrowEntity_removeReservationFromBooksAvailableToPickUp_thenItWorks() {
        //Arrange
        ReservationsAvailableToBorrowEntity reservation = constructReservationAvailableToBorrow();

        //Act
        reservationsAvailableForPickUpService.removeReservationFromBooksAvailableToPickUp(reservation);

        //Assert
        verify(reservationsAvailableForPickUpRepository, times(1)).deleteById(5);
        verify(reservationsAvailableForPickUpRepository, times(1)).flush();
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