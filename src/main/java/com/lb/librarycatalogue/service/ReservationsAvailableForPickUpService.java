package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.repository.BookReservationRepository;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReservationsAvailableForPickUpService {

    private final ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;
    private final BooksRepository booksRepository;
   private final BookReservationRepository bookReservationRepository;
    private final LibraryMemberRepository libraryMemberRepository;


    public ReservationsAvailableForPickUpService(ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository, BooksRepository booksRepository, BookReservationRepository bookReservationRepository, LibraryMemberRepository libraryMemberRepository) {
        this.reservationsAvailableForPickUpRepository = reservationsAvailableForPickUpRepository;
        this.booksRepository = booksRepository;
     this.bookReservationRepository = bookReservationRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }


    public List<ReservationsAvailableToBorrowEntity> getListsOfReservationPickedUp() {
        List<ReservationsAvailableToBorrowEntity> reservationsNotPickedUpOnTime = reservationsAvailableForPickUpRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getDeadlineDateToBorrowBook().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return reservationsNotPickedUpOnTime;
    }



    public void removeReservationFromBooksAvailableToPickUp(ReservationsAvailableToBorrowEntity reservation) {
        reservationsAvailableForPickUpRepository.deleteById(reservation.getId());
        reservationsAvailableForPickUpRepository.flush();
    }



}
