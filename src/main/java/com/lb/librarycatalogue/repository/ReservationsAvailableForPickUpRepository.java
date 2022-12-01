package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.entity.ReservationsAvailableToBorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface ReservationsAvailableForPickUpRepository extends JpaRepository <ReservationsAvailableToBorrowEntity, Integer> {

}
