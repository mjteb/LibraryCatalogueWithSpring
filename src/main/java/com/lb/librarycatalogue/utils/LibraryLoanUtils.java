package com.lb.librarycatalogue.utils;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.repository.BooksRepository;

public final class LibraryLoanUtils {
    static final String STATUS_AVAILABLE = "AVAILABLE";

    public static void updateBookRecordNumberOfCopiesAvailable(BooksRepository booksRepository, BooksEntity bookToUpdate) {
        long numberOfCopiesAvailable = bookToUpdate.getCopiesOfBooks().stream().filter(copy -> copy.getStatus().equals(STATUS_AVAILABLE)).count();
        bookToUpdate.setNumberOfCopiesAvailable((int) numberOfCopiesAvailable);
    }


}
