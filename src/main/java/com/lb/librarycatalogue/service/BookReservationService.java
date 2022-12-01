package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.repository.BookReservationRepository;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.utils.BookReservationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
public class BookReservationService {

    private final BookReservationRepository bookReservationRepository;
    private final BooksRepository booksRepository;



    BookReservationService(BookReservationRepository bookReservationRepository, BooksRepository booksRepository) {
        this.bookReservationRepository = bookReservationRepository;
        this.booksRepository = booksRepository;
    }


    public void reserveBook(ReservedBooksEntity reservedBooksEntity) {
        reservedBooksEntity.setDateBookReserved(LocalDate.now());
        reservedBooksEntity.setTitleOfReservedBook(booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get().getTitle());
        int numberOfReservations = booksRepository.findById(reservedBooksEntity.getIsbnOfReservedBook()).get().getNumberOfReservations();
        reservedBooksEntity.setPositionInLineForBook(numberOfReservations + 1);
        bookReservationRepository.save(reservedBooksEntity);
    }

    public void deleteReservation(ReservedBooksEntity reservedBooksEntity) {
        bookReservationRepository.deleteById(reservedBooksEntity.getId());
        BookReservationUtils.updateToBookRecordAfterDeletingReservation(booksRepository, reservedBooksEntity);
    }
}


