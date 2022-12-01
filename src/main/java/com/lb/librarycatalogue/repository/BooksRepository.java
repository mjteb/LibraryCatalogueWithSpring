package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BooksRepository extends JpaRepository <BooksEntity, String> {
    @Query
            (value = "select * " +
                    "from books " +
                    "where isbn = :isbn", nativeQuery = true)
    List<BooksEntity> getBookbyIsbn(@Param(value = "isbn") String isbn);

    @Query
            (value = "select * " +
                    "from books " +
                    "where title ilike :title", nativeQuery = true)
    List<BooksEntity> getBookbyTitle(@Param(value ="title") String title);

    @Query
            (value = "select * " +
                    "from books " +
                    "where author ilike :author", nativeQuery = true)
    List<BooksEntity> getBookAuthor(@Param(value ="author") String author);

    @Query
            (value = "select isbn " +
                    "from books " +
                    "where barcode = :barcode", nativeQuery = true)
    String findByBarcode(@Param(value ="barcode") String barcode);
}
