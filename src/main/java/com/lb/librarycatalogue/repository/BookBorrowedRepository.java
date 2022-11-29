package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.BooksBorrowedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookBorrowedRepository extends JpaRepository<BooksBorrowedEntity, Integer> {
}
