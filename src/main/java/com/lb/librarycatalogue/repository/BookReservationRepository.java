package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReservationRepository extends JpaRepository<ReservedBooksEntity, Integer> {
    @Modifying
    @Query
            (value = "delete  " +
                    "from books_reserved " +
                    "where id = :id", nativeQuery = true)
    void deleteBy(@Param(value = "id") int id);
}
