package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookReservationRepository extends JpaRepository<ReservedBooksEntity, Integer> {

    @Query
            (value = "delete  " +
                    "from books_reserved " +
                    "where isbn_of_reserved_book ilike :isbn and id_member ilike :cardNumber", nativeQuery = true)
    void deleteBy(@Param(value = "isbn") String isbn, @Param(value = "cardNumber") String cardNumber);
}
