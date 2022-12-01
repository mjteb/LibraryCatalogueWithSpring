package com.lb.librarycatalogue.utils;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.repository.BooksRepository;

public final class LibraryLoanUtils {

    public static void updateBookRecordAfterBorrowing(BooksRepository booksRepository, BooksBorrowed booksBorrowed) {
        BooksEntity recordOfBook = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get();
        recordOfBook.setNumberOfCopiesAvailable(recordOfBook.getNumberOfCopiesAvailable() - 1);
    }

    public static void updateBookRecordAfterReturning(BooksRepository booksRepository, BooksBorrowed booksBorrowed) {
        BooksEntity recordOfBook = booksRepository.findById(booksBorrowed.getIsbnOfBorrowedBook()).get();
        int numberOfCopiesAvailable = recordOfBook.getNumberOfCopiesAvailable() + 1;
        recordOfBook.setNumberOfCopiesAvailable(numberOfCopiesAvailable);
    }
}
