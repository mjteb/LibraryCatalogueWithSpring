package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.repository.BookReservationRepository;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationsAvailableForPickUpService {

    private final ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;
    private final BooksRepository booksRepository;
    private final BookReservationRepository bookReservationRepository;
    private final BookReservationService bookReservationService;


    public ReservationsAvailableForPickUpService(ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository, BooksRepository booksRepository, BookReservationRepository bookReservationRepository, BookReservationService bookReservationService) {
        this.reservationsAvailableForPickUpRepository = reservationsAvailableForPickUpRepository;
        this.booksRepository = booksRepository;
        this.bookReservationRepository = bookReservationRepository;
        this.bookReservationService = bookReservationService;
    }

    @Scheduled(cron = "0 1 * * *")
    public void verifyIfReservationPickedUp() {
        List<ReservationsAvailableToBorrowEntity> reservationsNotPickedUpOnTime = reservationsAvailableForPickUpRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getDeadlineDateToBorrowBook().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        if (reservationsNotPickedUpOnTime.size() >= 1) {
            reservationsNotPickedUpOnTime.forEach(this::updatesIfReservationNotPickedUp);
        }
    }

    public void updatesIfReservationNotPickedUp(ReservationsAvailableToBorrowEntity reservation) {
        BooksEntity bookToUpdate = booksRepository.findById(reservation.getIsbnOfReservedBook()).get();
        removeReservationFromBooksAvailableToPickUp(reservation);
        checkIfBookReserved(bookToUpdate);

    }

    public void removeReservationFromBooksAvailableToPickUp(ReservationsAvailableToBorrowEntity reservation) {
        reservationsAvailableForPickUpRepository.deleteById(reservation.getId());
        reservationsAvailableForPickUpRepository.flush();
    }

    public void checkIfBookReserved(BooksEntity bookToUpdate) {
        if (bookToUpdate.getReservations().size() > 0) {
            bookReservationService.updateNumberOfReservations(bookToUpdate);
            bookReservationService.updatePositionInLineForReservation(bookToUpdate);
            //TODO: bookborrowed: stateless a component has to be
//            bookReservationService.determineNextPersonBookReservedForAndCopyOfBook(bookToUpdate, booksBorrowed, reservationsAvailableForPickUpRepository);
        }
    }


    public void updatesAfterRemovingReservationFromBooksAvailableToPickUp(int id) {

    }


}
