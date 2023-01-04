package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.BooksBorrowedRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class LibraryFeesService {

    private final BooksBorrowedRepository booksBorrowedRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private double updatedLibraryFees = 0.00;


    public LibraryFeesService(BooksBorrowedRepository booksBorrowedRepository, LibraryMemberRepository libraryMemberRepository) {
        this.booksBorrowedRepository = booksBorrowedRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }


    @Transactional
    @Scheduled(cron = "0 0 6 * * *")
//    @Scheduled(cron = "*/10 * * * * *")
    public void checkIfBooksAreLate() {
        Set<String> membersWithLateBooks = booksBorrowedRepository.findAll()
                .stream()
                .filter(book -> book.getDueDate().isBefore(LocalDate.now()))
                .map(BooksBorrowed::getIdMember)
                .collect(Collectors.toSet());

        if (!membersWithLateBooks.isEmpty()) {
            membersWithLateBooks.forEach(this::calculateLateFeesForBooksCurrentlyBorrowed);
        }
    }

    public void verifyIfAnyBooksStillBorrowed(String cardNumber) {
        LibraryMemberEntity member = libraryMemberRepository.findById(cardNumber).get();
        if (member.getBooksBorrowedEntities().isEmpty()) {
            member.setLibraryFeesFromBooksCurrentlyBorrowed(0.00);
            member.setTotalLibraryFees(member.getLibraryFeesFromBooksCurrentlyBorrowed() + member.getLibraryFeesFromBooksReturned());
        }
    }

    public void calculateLateFeesForBooksCurrentlyBorrowed(String cardNumber) {
        LibraryMemberEntity member = libraryMemberRepository.findById(cardNumber).get();
        member.getBooksBorrowedEntities()
                .stream()
                .map(BooksBorrowed::getDueDate)
                .filter(dueDate -> dueDate.isBefore(LocalDate.now()))
                .forEach(date -> calculateLateFeesPerEachBookCurrentlyBorrowed(date, member));

        member.setTotalLibraryFees(member.getLibraryFeesFromBooksCurrentlyBorrowed() + member.getLibraryFeesFromBooksReturned());
        updatedLibraryFees = 0.00;
    }

    public void calculateLateFeesPerEachBookCurrentlyBorrowed(LocalDate dueDate, LibraryMemberEntity member) {
        long numberOfDaysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        double fines = (double) numberOfDaysLate * 0.25;
        updatedLibraryFees += fines;
        member.setLibraryFeesFromBooksCurrentlyBorrowed(updatedLibraryFees);
    }

    public void checkIfBooksReturnedLate(BooksBorrowed booksBorrowed) {
        LibraryMemberEntity member = libraryMemberRepository.findById(booksBorrowed.getIdMember()).get();
        LocalDate dueDateOfBook = booksBorrowed.getDueDate();
        if (dueDateOfBook.isBefore(LocalDate.now())) {
            calculateLateFeesForBookBeingReturned(booksBorrowed.getDueDate(), member);
        }
    }

    public void calculateLateFeesForBookBeingReturned(LocalDate dueDate, LibraryMemberEntity member) {
        long numberOfDaysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        double fines = (double) numberOfDaysLate * 0.25;
        double libraryFeesFromBooksReturned = member.getLibraryFeesFromBooksReturned();
        double updatedlibraryFeesFromBooksReturned = libraryFeesFromBooksReturned + fines;
        member.setLibraryFeesFromBooksReturned(updatedlibraryFeesFromBooksReturned);
    }

}
