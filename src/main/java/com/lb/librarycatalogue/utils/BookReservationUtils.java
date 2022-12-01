package com.lb.librarycatalogue.utils;

import com.lb.librarycatalogue.entity.*;
import com.lb.librarycatalogue.repository.BookReservationRepository;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import com.lb.librarycatalogue.service.BookReservationService;

import java.time.LocalDate;

public final class BookReservationUtils {
private final BookReservationService bookReservationService;

    public BookReservationUtils(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    public static void updateToBookRecordAfterDeletingReservation(BooksRepository booksRepository, ReservedBooksEntity reservedBooksEntity) {
        BooksEntity bookRecord = booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get();
        updatePositionInLineForReservation(bookRecord);
        updateNumberOfReservations(bookRecord);
    }

    private static void updatePositionInLineForReservation(BooksEntity bookRecord) {
    bookRecord.getReservations().stream().forEach(reservation ->  reservation.setPositionInLineForBook(reservation.getPositionInLineForBook() - 1));
    }

    public static void updateNumberOfReservations (BooksEntity bookRecord) {
        int numberOfReservations = bookRecord.getNumberOfReservations();
        bookRecord.setNumberOfReservations(numberOfReservations - 1);
    }

    public static void checkIfBookReserved(BooksRepository booksRepository, BooksBorrowed booksBorrowed, BookReservationRepository bookReservationRepository, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        BooksEntity bookRecord = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get();
        int numberOfReservations = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get().getNumberOfReservations();
        if (numberOfReservations >= 1) {
            updatesAfterReturningBookWithReservations(bookRecord, booksBorrowed, bookReservationRepository, reservationsAvailableForPickUpRepository);
        }
    }

    private static void updatesAfterReturningBookWithReservations(BooksEntity bookRecord, BooksBorrowed booksBorrowed, BookReservationRepository bookReservationRepository, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        addBookToReservationsAvailableForPickUp(booksBorrowed, bookRecord, reservationsAvailableForPickUpRepository);
        bookReservationRepository.deleteById(booksBorrowed.getId());
        updatePositionInLineForReservation(bookRecord);
        updateNumberOfReservations(bookRecord);
    }

    public static void addBookToReservationsAvailableForPickUp (BooksBorrowed booksBorrowed, BooksEntity bookRecord,  ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        ReservationsAvailableToBorrowEntity newReservationAvailableForPickUp = ReservationsAvailableToBorrowEntity.builder()
                .isbnOfReservedBook(bookRecord.getIsbn())
                .dateBookAvailableToBorrow(LocalDate.now())
                .deadlineDateToBorrowBook(LocalDate.now().plusWeeks(1))
                .titleOfReservedBook(bookRecord.getTitle())
                .barcodeOfReservedBook(booksBorrowed.getIdBook())
                .build();
        reservationsAvailableForPickUpRepository.save(newReservationAvailableForPickUp);
    }

}
