package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsAvailableForPickUpRepository extends JpaRepository<ReservationsAvailableToBorrowEntity, Integer> {

}
