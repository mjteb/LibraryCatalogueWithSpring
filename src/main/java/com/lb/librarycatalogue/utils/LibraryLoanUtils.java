package com.lb.librarycatalogue.utils;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.repository.BooksRepository;

public final class LibraryLoanUtils {

    public static void updateBookRecordNumberOfCopiesAvailable(BooksRepository booksRepository, BooksEntity bookToUpdate) {
        long numberOfCopiesAvailable =  bookToUpdate.getCopiesOfBooks().stream().filter(copy -> copy.getStatus().equals("AVAILABLE")).count();
        bookToUpdate.setNumberOfCopiesAvailable((int)numberOfCopiesAvailable);
    }




}
