package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface LibraryMemberRepository extends JpaRepository <LibraryMemberEntity, String> {

    @Modifying
    @Transactional
    @Query(value = "update library_member " +
            "set membership_expiration_date = :newExpirationDate " +
            "where card_number ilike :cardNumber" , nativeQuery = true)
    void renewMembership(@Param(value="cardNumber") String cardNumber, @Param(value="newExpirationDate")LocalDate newExpirationDate);
}
