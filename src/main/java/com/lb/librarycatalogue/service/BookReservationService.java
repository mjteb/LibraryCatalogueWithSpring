package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.*;
import com.lb.librarycatalogue.repository.BookReservationRepository;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import com.lb.librarycatalogue.utils.LibraryMemberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookReservationService {

    private final BookReservationRepository bookReservationRepository;
    private final BooksRepository booksRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;

    BookReservationService(BookReservationRepository bookReservationRepository, BooksRepository booksRepository, LibraryMemberRepository libraryMemberRepository, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        this.bookReservationRepository = bookReservationRepository;
        this.booksRepository = booksRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.reservationsAvailableForPickUpRepository = reservationsAvailableForPickUpRepository;
    }
    
    public void reserveBook(ReservedBooksEntity reservedBooksEntity) {
        BooksEntity bookToUpdate = booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get();
        String cardNumber = reservedBooksEntity.getIdMember();
        LibraryMemberUtils.checkIfMemberCanReserve(reservedBooksEntity, libraryMemberRepository, booksRepository);
        reservedBooksEntity.setDateBookReserved(LocalDate.now());
        reservedBooksEntity.setTitleOfReservedBook(booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get().getTitle());
        reservedBooksEntity.setPositionInLineForBook(bookToUpdate.getReservations().size() + 1);
        bookReservationRepository.save(reservedBooksEntity);
        booksRepository.flush();
        LibraryMemberUtils.updateMemberNumberOfReservations(libraryMemberRepository, bookReservationRepository, cardNumber);
        updateNumberOfReservations(bookToUpdate);
    }

    public void checkIfBookReserved(BooksRepository booksRepository, BooksBorrowed booksBorrowed, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        BooksEntity bookToUpdate = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get();
        int numberOfReservations = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get().getReservations().size();
        if (numberOfReservations >= 1) {
            stepsForReturningBookWithReservations(bookToUpdate, booksBorrowed);
        }
    }

    public void stepsForReturningBookWithReservations(BooksEntity bookToUpdate, BooksBorrowed booksBorrowed) {
        List<ReservedBooksEntity> reservationToDelete = bookToUpdate.getReservations().stream()
                .filter(reservation -> reservation.getPositionInLineForBook() == 1)
                .collect(Collectors.toList());

        int id = reservationToDelete.get(0).getId();
        String cardNumber = reservationToDelete.get(0).getIdMember();
        String barcode = booksBorrowed.getIdBook();
        updatePositionInLineForReservation(bookToUpdate);
        deleteReservation(id);
        addBookToReservationsAvailableForPickUp(cardNumber, barcode, bookToUpdate);
        updateNumberOfReservations(bookToUpdate);
    }

    public void stepForDeletingReservation(int id) {
        String cardNumber = bookReservationRepository.findById(id).get().getIdMember();
        ReservedBooksEntity reservedBooksEntity = bookReservationRepository.findById(id).get();
        BooksEntity bookToUpdate = booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get();
        deleteReservation(id);
        updateNumberOfReservations(bookToUpdate);
        updatePositionInLineForReservation(bookToUpdate);
        LibraryMemberUtils.updateMemberNumberOfReservations(libraryMemberRepository, bookReservationRepository, cardNumber);
    }


    public void addBookToReservationsAvailableForPickUp(String cardNumber, String barcode, BooksEntity bookToUpdate) {
        ReservationsAvailableToBorrowEntity newReservationAvailableForPickUp = ReservationsAvailableToBorrowEntity.builder()
                .isbnOfReservedBook(bookToUpdate.getIsbn())
                .idMember(cardNumber)
                .dateBookAvailableToBorrow(LocalDate.now())
                .deadlineDateToBorrowBook(LocalDate.now().plusWeeks(1))
                .titleOfReservedBook(bookToUpdate.getTitle())
                .barcodeOfReservedBook(barcode)
                .build();
        reservationsAvailableForPickUpRepository.save(newReservationAvailableForPickUp);
        reservationsAvailableForPickUpRepository.flush();
    }

    public void deleteReservation(int id) {
        bookReservationRepository.deleteBy(id);
        booksRepository.flush();
    }


    public void updatePositionInLineForReservation(BooksEntity bookToUpdate) {
        AtomicInteger counter = new AtomicInteger(1);
        bookToUpdate.getReservations().stream().forEach(reservation -> {
            if (reservation.getPositionInLineForBook() != 1) {
                reservation.setPositionInLineForBook(counter.getAndIncrement());
            }
        });
    }


    public void updateNumberOfReservations(BooksEntity bookToUpdate) {
        long numberOfReservations = bookReservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getIsbnOfReservedBook().equals(bookToUpdate.getIsbn()))
                .count();

        bookToUpdate.setNumberOfReservations((int) numberOfReservations);
    }




}


