package com.lb.librarycatalogue.utils;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.repository.BookReservationRepository;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;

import java.time.LocalDate;

public final class LibraryMemberUtils {

private static final double AMOUNT_OF_FEES_THAT_BLOCK_ACCOUNT = 5.00;
private static final int MAX_NUMBER_OF_BORROWED_BOOKS = 20;
private static final int MAX_NUMBER_OF_RESERVATIONS_ALLOWED = 15;

    public static void verifyMemberCanBorrowBook (LibraryMemberRepository libraryMemberRepository, BooksBorrowed booksBorrowed) {
        LibraryMemberEntity libraryMember = libraryMemberRepository.findById(booksBorrowed.getIdMember()).get();
        checkUnpaidFees(libraryMember);
        checkMembershipExpiration(libraryMember);
        checkNumberOfBooksBorrowed(libraryMember);
    }


    private static void checkUnpaidFees(LibraryMemberEntity libraryMember) {
        if (libraryMember.getOutstandingLateFees() > AMOUNT_OF_FEES_THAT_BLOCK_ACCOUNT) {
            throw new RuntimeException("Library fees must be paid before borrowing");
        }
    }

    private static void checkMembershipExpiration(LibraryMemberEntity libraryMember) {
        if (libraryMember.getMembershipExpirationDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Membership is expired");
        }
    }

    private static void checkNumberOfBooksBorrowed(LibraryMemberEntity libraryMember) {
        if (libraryMember.getNumberOfBooksBorrowed() + 1 >= MAX_NUMBER_OF_BORROWED_BOOKS) {
            throw new RuntimeException("Number of borrowed books exceeded");
        }
    }

    public static void updateMemberProfileAfterBorrowing(LibraryMemberRepository libraryMemberRepository, BooksBorrowed booksBorrowed) {
        LibraryMemberEntity libraryMember = libraryMemberRepository.findById(booksBorrowed.getIdMember()).get();
        libraryMember.setNumberOfBooksBorrowed(libraryMember.getNumberOfBooksBorrowed() + 1);
    }

    public static void updateMemberProfileAfterReturning(LibraryMemberRepository libraryMemberRepository, BooksBorrowed booksBorrowed) {
        LibraryMemberEntity libraryMember = libraryMemberRepository.findById(booksBorrowed.getIdMember()).get();
        int numberOfBooksBorrowed = libraryMember.getNumberOfBooksBorrowed() - 1;
        libraryMember.setNumberOfBooksBorrowed(numberOfBooksBorrowed);
    }



    public static void updateMemberNumberOfReservations(LibraryMemberRepository libraryMemberRepository, BookReservationRepository bookReservationRepository, String cardNumber) {
        LibraryMemberEntity libraryMember = libraryMemberRepository.findById(cardNumber).get();
        long numberOfReservations = bookReservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getIdMember().equals(libraryMember.getCardNumber())).count();
        libraryMember.setNumberOfBooksReserved((int)numberOfReservations);
    }

    public static void checkIfMemberCanReserve(ReservedBooksEntity reservedBooksEntity, LibraryMemberRepository libraryMemberRepository, BooksRepository booksRepository) {
        String cardNumber = reservedBooksEntity.getIdMember();
        LibraryMemberEntity libraryMember = libraryMemberRepository.findById(cardNumber).get();
        checkMembershipExpiration(libraryMember);
        checkIfMemberAlreadyReservedBook(libraryMember, reservedBooksEntity, booksRepository);
        checkNumberOfReservations(libraryMember);
    }

    private static void checkIfMemberAlreadyReservedBook(LibraryMemberEntity libraryMember, ReservedBooksEntity reservedBooksEntity, BooksRepository booksRepository) {
        BooksEntity bookRecord = booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get();
        boolean memberAlreadyReserved = bookRecord.getReservations().stream().anyMatch(reservation -> reservation.getIdMember().equals(libraryMember.getCardNumber()));
        if (memberAlreadyReserved) {
            throw new RuntimeException("Library member already reserved book");
        }
    }

    private static void checkNumberOfReservations(LibraryMemberEntity libraryMember) {
        int numberOfMembersReservations = libraryMember.getNumberOfBooksReserved();
        if (numberOfMembersReservations > MAX_NUMBER_OF_RESERVATIONS_ALLOWED) {
            throw new RuntimeException("Max number of reservations made");
        }
    }
}
