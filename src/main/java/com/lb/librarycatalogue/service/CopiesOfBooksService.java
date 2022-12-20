package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.repository.CopiesOfBooksRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Copies;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CopiesOfBooksService {

    private final CopiesOfBooksRepository copiesOfBooksRepository;
    private final ReservationsAvailableForPickUpService reservationsAvailableForPickUpService;


    CopiesOfBooksService(CopiesOfBooksRepository copiesOfBooksRepository, ReservationsAvailableForPickUpService reservationsAvailableForPickUpService) {
        this.copiesOfBooksRepository = copiesOfBooksRepository;
        this.reservationsAvailableForPickUpService = reservationsAvailableForPickUpService;
    }


    public void changeCopyStatusToOnLoanAndAddDueDate(String barcode) {
        CopiesOfBooksEntity copyToUpdate = copiesOfBooksRepository.findById(barcode).get();
        copyToUpdate.setDueDate(LocalDate.now().plusWeeks(3));
        copyToUpdate.setStatus("ON LOAN");
    }



    public void changeCopyStatusToAvailableAndRemoveDueDate(String barcode) {
        CopiesOfBooksEntity copyToUpdate = copiesOfBooksRepository.findById(barcode).get();
        copyToUpdate.setDueDate(null);
        copyToUpdate.setStatus("AVAILABLE");
    }

    public void updateCopyAvailableForPickUp(String barcode) {
        CopiesOfBooksEntity copyToUpdate = copiesOfBooksRepository.findById(barcode).get();
        copyToUpdate.setDueDate(null);
        copyToUpdate.setStatus("ON HOLD FOR PICK UP");
    }

    public void verifyCopyIsAvailableForLoan(BooksBorrowed booksBorrowed, ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        List<ReservationsAvailableToBorrowEntity> reservation = reservationsAvailableForPickUpRepository.findAll()
                .stream()
                .filter(copy -> copy.getBarcodeOfReservedBook().equals(booksBorrowed.getIdBook()))
                .filter(copy -> copy.getIdMember().equals(booksBorrowed.getIdMember()))
                .collect(Collectors.toList());
        CopiesOfBooksEntity copyOfBook = copiesOfBooksRepository.findById(booksBorrowed.getIdBook()).get();
        if (!copyOfBook.getStatus().equals("AVAILABLE") && reservation.size() < 1) {
            throw new RuntimeException("Copy is unavailable to borrow. Please make a reservation");
        } else if (reservation.size() >= 1) {
//            reservationsAvailableForPickUpService.removeReservationFromBooksAvailableToPickUp(reservation.get(0));
            reservationsAvailableForPickUpRepository.deleteById(reservation.get(0).getId());
        }

    }

    public void deleteCopy(String barcode) {
        CopiesOfBooksEntity copy = copiesOfBooksRepository.findById(barcode).orElseThrow(() -> new RuntimeException("An invalid barcode was entered"));
            copiesOfBooksRepository.deleteById(barcode);
    }
}

