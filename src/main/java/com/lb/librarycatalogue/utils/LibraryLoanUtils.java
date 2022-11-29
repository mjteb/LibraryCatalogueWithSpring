package com.lb.librarycatalogue.utils;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;

public final class LibraryLoanUtils {

    public static void updateBookRecord(BooksRepository booksRepository, String isbn) {
        BooksEntity recordOfBook = booksRepository.findById(isbn).get();
        recordOfBook.setNumberOfCopiesAvailable(recordOfBook.getNumberOfCopiesAvailable() - 1);
    }

    public static void updateMemberProfile(LibraryMemberRepository libraryMemberRepository, String cardNumber, String barcode) {
        LibraryMemberEntity libraryMember = libraryMemberRepository.findById(cardNumber).get();
        libraryMember.setNumberOfBooksBorrowed(libraryMember.getNumberOfBooksBorrowed() + 1);
    }
}
