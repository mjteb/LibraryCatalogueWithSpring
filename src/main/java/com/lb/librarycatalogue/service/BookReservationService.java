package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.entity.ReservedBooksEntity;
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

    BookReservationService(BookReservationRepository bookReservationRepository, BooksRepository booksRepository, LibraryMemberRepository libraryMemberRepository) {
        this.bookReservationRepository = bookReservationRepository;
        this.booksRepository = booksRepository;
        this.libraryMemberRepository = libraryMemberRepository;

    }
    
    public void reserveBook(ReservedBooksEntity reservedBooksEntity) {
        BooksEntity bookToUpdate = booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get();
        LibraryMemberUtils.checkIfMemberCanReserve(reservedBooksEntity, libraryMemberRepository, booksRepository);
        reservedBooksEntity.setDateBookReserved(LocalDate.now());
        reservedBooksEntity.setTitleOfReservedBook(booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get().getTitle());
        reservedBooksEntity.setPositionInLineForBook(bookToUpdate.getReservations().size() + 1);
        bookReservationRepository.save(reservedBooksEntity);
        booksRepository.flush();
        LibraryMemberUtils.updateMemberProfileAfterReserving(libraryMemberRepository, reservedBooksEntity);
        updateNumberOfReservations(bookToUpdate);
    }

    public void deleteReservation(int id) {
        String cardNumber = bookReservationRepository.findById(id).get().getIdMember();
        ReservedBooksEntity reservedBooksEntity = bookReservationRepository.findById(id).get();
        BooksEntity bookToUpdate = booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get();
        LibraryMemberUtils.updateMemberProfileAfterDeletingReserving(libraryMemberRepository, cardNumber);
        bookReservationRepository.deleteById(id);
        booksRepository.flush();
        updateNumberOfReservations(bookToUpdate);
        updatePositionInLineForReservation(bookToUpdate);
    }
//
//    public void deleteReservationForMemberWhoWillReceiveBookNext(BooksEntity bookToUpdate) {
//        List<Integer> reservationToDelete = bookToUpdate.getReservations().stream()
//                .filter(reservation -> reservation.getPositionInLineForBook() == 1)
//                .map(ReservedBooksEntity::getId)
//                .collect(Collectors.toList());
//        int idToDelete = reservationToDelete.get(0);
//        bookReservationRepository.deleteById(idToDelete);
//    }


    public void updatePositionInLineForReservation(BooksEntity bookToUpdate) {
        AtomicInteger counter = new AtomicInteger(1);
        bookToUpdate.getReservations().stream().forEach(reservation -> reservation.setPositionInLineForBook(counter.getAndIncrement()));
    }


    public void updateNumberOfReservations(BooksEntity bookToUpdate) {
        long numberOfReservations = bookReservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getIsbnOfReservedBook().equals(bookToUpdate.getIsbn()))
                .count();

        bookToUpdate.setNumberOfReservations((int) numberOfReservations);
    }

    private void updatesAfterReturningBookWithReservations(BooksEntity bookToUpdate, BooksBorrowed booksBorrowed, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
//        determineNextPersonBookReservedForAndCopyOfBook(bookToUpdate, booksBorrowed, reservationsAvailableForPickUpRepository);
//        updateNumberOfReservations(bookToUpdate, bookReservationRepository);
//        updatePositionInLineForReservation(bookToUpdate);
        deleteReservationForMemberWhoWillReceiveBookNext(bookToUpdate);
    }

    public void deleteReservationForMemberWhoWillReceiveBookNext(BooksEntity bookToUpdate) {
        List<Integer> reservationToDelete = bookToUpdate.getReservations().stream()
                .filter(reservation -> reservation.getPositionInLineForBook() == 1)
                .map(ReservedBooksEntity::getId)
                .collect(Collectors.toList());
        int idToDelete = reservationToDelete.get(0);
        bookReservationRepository.deleteById(idToDelete);
        bookReservationRepository.flush();
    }

    public void determineNextPersonBookReservedForAndCopyOfBook(BooksEntity bookToUpdate, BooksBorrowed booksBorrowed, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        List<String> libraryMember = bookToUpdate.getReservations().stream()
                .filter(reservation -> reservation.getPositionInLineForBook() == 1)
                .map(ReservedBooksEntity::getIdMember)
                .collect(Collectors.toList());
        String cardNumber = libraryMember.get(0);
        String barcode = booksBorrowed.getIdBook();
        addBookToReservationsAvailableForPickUp(cardNumber, barcode, bookToUpdate, reservationsAvailableForPickUpRepository);
    }

    public void checkIfBookReserved(BooksRepository booksRepository, BooksBorrowed booksBorrowed, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        BooksEntity bookToUpdate = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get();
        int numberOfReservations = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get().getReservations().size();
        if (numberOfReservations >= 1) {
            updatesAfterReturningBookWithReservations(bookToUpdate, booksBorrowed, reservationsAvailableForPickUpRepository);
        }
    }

    public void addBookToReservationsAvailableForPickUp(String cardNumber, String barcode, BooksEntity bookToUpdate, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
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



}


