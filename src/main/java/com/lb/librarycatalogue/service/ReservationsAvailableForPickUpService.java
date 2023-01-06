package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReservationsAvailableForPickUpService {

    private final ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;


    public ReservationsAvailableForPickUpService(ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository) {
        this.reservationsAvailableForPickUpRepository = reservationsAvailableForPickUpRepository;

    }

    public List<ReservationsAvailableToBorrowEntity> getListsOfReservationPickedUp() {
        List<ReservationsAvailableToBorrowEntity> reservationsNotPickedUpOnTime = reservationsAvailableForPickUpRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getDeadlineDateToBorrowBook().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return reservationsNotPickedUpOnTime;
    }

    public void removeReservationFromBooksAvailableToPickUp(ReservationsAvailableToBorrowEntity reservation) {
        reservationsAvailableForPickUpRepository.deleteById(reservation.getId());
        reservationsAvailableForPickUpRepository.flush();
    }


}
