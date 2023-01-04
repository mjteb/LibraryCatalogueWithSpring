package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksBorrowedRepository extends JpaRepository<BooksBorrowed, Integer> {


}
