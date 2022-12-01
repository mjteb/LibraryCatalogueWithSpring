package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryLoanSystemRepository extends JpaRepository <BooksBorrowed, Integer> {


}
