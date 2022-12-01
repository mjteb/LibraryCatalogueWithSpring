package com.lb.librarycatalogue.utils;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;

import java.time.LocalDate;

public final class LibraryMemberUtils {

private static final double AMOUNT_OF_FEES_THAT_BLOCK_ACCOUNT = 5.00;
private static final int MAX_NUMBER_OF_BORROWED_BOOKS = 15;

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
}
