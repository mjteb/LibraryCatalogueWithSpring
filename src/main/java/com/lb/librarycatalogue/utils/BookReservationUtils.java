//package com.lb.librarycatalogue.utils;
//
//import com.lb.librarycatalogue.entity.BooksBorrowed;
//import com.lb.librarycatalogue.entity.BooksEntity;
//import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
//import com.lb.librarycatalogue.entity.ReservedBooksEntity;
//import com.lb.librarycatalogue.repository.BookReservationRepository;
//import com.lb.librarycatalogue.repository.BooksRepository;
//import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
//import com.lb.librarycatalogue.service.BookReservationService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public final class BookReservationUtils {
//
//    private final BookReservationService bookReservationService;
//    private final ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;
//
//    public BookReservationUtils(BookReservationService bookReservationService, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
//        this.bookReservationService = bookReservationService;
//        this.reservationsAvailableForPickUpRepository = reservationsAvailableForPickUpRepository;
//    }
//
//
//}
//
