package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.repository.CopiesOfBooksRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CopiesOfBooksService {

    private final CopiesOfBooksRepository copiesOfBooksRepository;
    static final String STATUS_AVAILABLE = "AVAILABLE";
    static final String STATUS_ON_HOLD = "ON HOLD FOR PICK UP";
    static final String STATUS_ON_LOAN = "ON LOAN";


    CopiesOfBooksService(CopiesOfBooksRepository copiesOfBooksRepository) {
        this.copiesOfBooksRepository = copiesOfBooksRepository;

    }


    public void changeCopyStatusToOnLoanAndAddDueDate(String barcode) {
        CopiesOfBooksEntity copyToUpdate = copiesOfBooksRepository.findById(barcode)
                .orElseThrow(() -> new RuntimeException("An invalid barcode was entered"));
        copyToUpdate.setDueDate(LocalDate.now().plusWeeks(3));
        copyToUpdate.setStatus(STATUS_ON_LOAN);
    }


    public void changeCopyStatusToAvailableAndRemoveDueDate(String barcode) {
        CopiesOfBooksEntity copyToUpdate = copiesOfBooksRepository.findById(barcode)
                .orElseThrow(() -> new RuntimeException("An invalid barcode was entered"));
        copyToUpdate.setDueDate(null);
        copyToUpdate.setStatus(STATUS_AVAILABLE);
    }

    public void updateCopyAvailableForPickUp(String barcode) {
        CopiesOfBooksEntity copyToUpdate = copiesOfBooksRepository.findById(barcode).get();
        copyToUpdate.setDueDate(null);
        copyToUpdate.setStatus(STATUS_ON_HOLD);
    }

    public void verifyCopyIsAvailableForLoan(BooksBorrowed booksBorrowed, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        List<ReservationsAvailableToBorrowEntity> reservation = reservationsAvailableForPickUpRepository.findAll()
                .stream()
                .filter(copy -> copy.getBarcodeOfReservedBook().equals(booksBorrowed.getIdBook()))
                .filter(copy -> copy.getIdMember().equals(booksBorrowed.getIdMember()))
                .collect(Collectors.toList());
        CopiesOfBooksEntity copyOfBook = copiesOfBooksRepository.findById(booksBorrowed.getIdBook()).get();
        if (!copyOfBook.getStatus().equals(STATUS_AVAILABLE) && reservation.size() < 1) {
            throw new RuntimeException("Copy is unavailable to borrow. Please make a reservation");
        } else if (reservation.size() >= 1) {
            reservationsAvailableForPickUpRepository.deleteById(reservation.get(0).getId());
        }

    }

    public void deleteCopy(String barcode) {
        CopiesOfBooksEntity copy = copiesOfBooksRepository.findById(barcode)
                .orElseThrow(() -> new RuntimeException("An invalid barcode was entered"));
        copiesOfBooksRepository.delete(copy);
    }
}

