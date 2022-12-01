package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.BooksMapper;
import com.lb.librarycatalogue.mapper.BorrowedBooksMapper;
import com.lb.librarycatalogue.mapper.CopiesOfBooksMapper;
import com.lb.librarycatalogue.mapper.LibraryMemberMapper;
import com.lb.librarycatalogue.mapper.pojos.BorrowedBooksDto;
import com.lb.librarycatalogue.service.LibraryLoanSystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/libraryloansystem")
public class LibraryLoanSystemController {



    private final CopiesOfBooksMapper copiesOfBooksMapper;
    private final BooksMapper booksMapper;
    private final LibraryMemberMapper libraryMemberMapper;
    private final BorrowedBooksMapper borrowedBooksMapper;
    private final LibraryLoanSystemService libraryLoanSystemService;

    public LibraryLoanSystemController(LibraryMemberMapper libraryMemberMapper, CopiesOfBooksMapper copiesOfBooksMapper, BooksMapper booksMapper, BorrowedBooksMapper borrowedBooksMapper, LibraryLoanSystemService libraryLoanSystemService) {
        this.copiesOfBooksMapper = copiesOfBooksMapper;
        this.libraryMemberMapper = libraryMemberMapper;
        this.booksMapper = booksMapper;
        this.borrowedBooksMapper = borrowedBooksMapper;
        this.libraryLoanSystemService = libraryLoanSystemService;
    }


    @PostMapping(value = "/borrowbook")
    ResponseEntity<Void> borrowBook (@RequestBody BorrowedBooksDto borrowedBooksDto) {
        libraryLoanSystemService.borrowBook(borrowedBooksMapper.mapBorrowedBooksDtoToEntity(borrowedBooksDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/returnbook")
    ResponseEntity<Void> returnBook (@RequestBody BorrowedBooksDto borrowedBooksDto) {
        libraryLoanSystemService.returnBook(borrowedBooksMapper.mapBorrowedBooksDtoToEntity(borrowedBooksDto));
        return ResponseEntity.noContent().build();
    }

}
