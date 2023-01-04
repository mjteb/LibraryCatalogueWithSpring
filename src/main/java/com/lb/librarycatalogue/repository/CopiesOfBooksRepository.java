package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopiesOfBooksRepository extends JpaRepository<CopiesOfBooksEntity, String> {

}
