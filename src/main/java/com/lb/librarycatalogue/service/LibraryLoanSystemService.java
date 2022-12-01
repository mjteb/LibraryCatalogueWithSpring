package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.repository.*;
import com.lb.librarycatalogue.utils.BookReservationUtils;
import com.lb.librarycatalogue.utils.LibraryLoanUtils;
import com.lb.librarycatalogue.utils.LibraryMemberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
public class LibraryLoanSystemService {

    private final LibraryMemberRepository libraryMemberRepository;
    private final LibraryLoanSystemRepository libraryLoanSystemRepository;
    private final BooksRepository booksRepository;
    private final BookReservationRepository bookReservationRepository;
    private final ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;


    public LibraryLoanSystemService(LibraryMemberRepository libraryMemberRepository, LibraryLoanSystemRepository libraryLoanSystemRepository, BooksRepository booksRepository, BookReservationService bookReservationService, BookReservationRepository bookReservationRepository, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.libraryLoanSystemRepository = libraryLoanSystemRepository;
        this.booksRepository = booksRepository;
        this.bookReservationRepository = bookReservationRepository;
        this.reservationsAvailableForPickUpRepository = reservationsAvailableForPickUpRepository;
    }


    public void borrowBook(BooksBorrowed booksBorrowed) {
        LibraryMemberUtils.verifyMemberCanBorrowBook(libraryMemberRepository, booksBorrowed);
        BooksBorrowed copyBorrowed = BooksBorrowed.builder()
                .idBook(booksBorrowed.getIdBook())
                .idMember(booksBorrowed.getIdMember())
                .isbnOfBorrowedBook(booksBorrowed.getIsbnOfBorrowedBook())
                .dateBookBorrowed(LocalDate.now())
                .dueDate(LocalDate.now().plusWeeks(3))
                .build();
        libraryLoanSystemRepository.save(copyBorrowed);
        LibraryLoanUtils.updateBookRecordAfterBorrowing(booksRepository, booksBorrowed);
        LibraryMemberUtils.updateMemberProfileAfterBorrowing(libraryMemberRepository, booksBorrowed);
    }

    public void returnBook(BooksBorrowed booksBorrowed) {
        LibraryLoanUtils.updateBookRecordAfterReturning(booksRepository, booksBorrowed);
        LibraryMemberUtils.updateMemberProfileAfterReturning(libraryMemberRepository, booksBorrowed);
        BookReservationUtils.checkIfBookReserved(booksRepository, booksBorrowed, bookReservationRepository, reservationsAvailableForPickUpRepository);
        libraryLoanSystemRepository.deleteById(booksBorrowed.getId());
    }
}

