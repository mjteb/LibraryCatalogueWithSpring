package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.repository.*;
import com.lb.librarycatalogue.utils.LibraryLoanUtils;
import com.lb.librarycatalogue.utils.LibraryMemberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class LibraryLoanSystemService {

    private final LibraryMemberRepository libraryMemberRepository;
    private final LibraryLoanSystemRepository libraryLoanSystemRepository;
    private final BooksRepository booksRepository;
    private final ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;
    private final BookReservationService bookReservationService;
    private final CopiesOfBooksService copiesOfBooksService;
    private final LibraryFeesService libraryFeesService;
    private final BooksBorrowedRepository booksBorrowedRepository;


    public LibraryLoanSystemService(LibraryMemberRepository libraryMemberRepository, LibraryLoanSystemRepository libraryLoanSystemRepository, BooksRepository booksRepository, BookReservationService bookReservationService, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository, CopiesOfBooksService copiesOfBooksService, LibraryFeesService libraryFeesService, BooksBorrowedRepository booksBorrowedRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.libraryLoanSystemRepository = libraryLoanSystemRepository;
        this.booksRepository = booksRepository;
        this.reservationsAvailableForPickUpRepository = reservationsAvailableForPickUpRepository;
        this.bookReservationService = bookReservationService;
        this.copiesOfBooksService = copiesOfBooksService;
        this.libraryFeesService = libraryFeesService;
        this.booksBorrowedRepository = booksBorrowedRepository;
    }

    @Transactional
    public void borrowBook(BooksBorrowed booksBorrowed) {
        LibraryMemberUtils.verifyMemberCanBorrowBook(libraryMemberRepository, booksBorrowed);
        copiesOfBooksService.verifyCopyIsAvailableForLoan(booksBorrowed, reservationsAvailableForPickUpRepository);
        BooksBorrowed copyBorrowed = BooksBorrowed.builder()
                .idBook(booksBorrowed.getIdBook())
                .idMember(booksBorrowed.getIdMember())
                .title(booksBorrowed.getTitle())
                .isbnOfBorrowedBook(booksBorrowed.getIsbnOfBorrowedBook())
                .dateBookBorrowed(LocalDate.now())
                .dueDate(LocalDate.now().plusWeeks(3))
                .build();
        libraryLoanSystemRepository.saveAndFlush(copyBorrowed);
        BooksEntity bookToUpdate = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get();
        LibraryMemberUtils.updateNumberOfBooksBorrowed(libraryMemberRepository, booksBorrowed);
        copiesOfBooksService.changeCopyStatusToOnLoanAndAddDueDate(booksBorrowed.getIdBook());
        LibraryLoanUtils.updateBookRecordNumberOfCopiesAvailable(booksRepository, bookToUpdate);
    }


    @Transactional
    public void returnBook(int id) {
        BooksBorrowed booksBorrowed = booksBorrowedRepository.findById(id).get();
        libraryFeesService.checkIfBooksReturnedLate(booksBorrowed);
        bookReservationService.checkIfBookReserved(booksRepository, booksBorrowed, reservationsAvailableForPickUpRepository);
        deleteBookFromBorrowedBooks(booksBorrowed);
        LibraryMemberUtils.updateNumberOfBooksBorrowed(libraryMemberRepository, booksBorrowed);
        libraryFeesService.verifyIfAnyBooksStillBorrowed(booksBorrowed.getIdMember());
        libraryFeesService.calculateLateFeesForBooksCurrentlyBorrowed(booksBorrowed.getIdMember());
    }

    public void deleteBookFromBorrowedBooks(BooksBorrowed booksBorrowed) {
        libraryLoanSystemRepository.delete(booksBorrowed);
        libraryLoanSystemRepository.flush();
    }
}

