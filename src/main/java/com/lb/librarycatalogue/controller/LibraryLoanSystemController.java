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


    private final BorrowedBooksMapper borrowedBooksMapper;
    private final LibraryLoanSystemService libraryLoanSystemService;

    public LibraryLoanSystemController(BorrowedBooksMapper borrowedBooksMapper, LibraryLoanSystemService libraryLoanSystemService) {
        this.borrowedBooksMapper = borrowedBooksMapper;
        this.libraryLoanSystemService = libraryLoanSystemService;
    }


    @PostMapping(value = "/borrowbook")
    ResponseEntity<Void> borrowBook(@RequestBody BorrowedBooksDto borrowedBooksDto) {
        libraryLoanSystemService.borrowBook(borrowedBooksMapper.mapBorrowedBooksDtoToEntity(borrowedBooksDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/returnbook")
    ResponseEntity<Void> returnBook(@RequestParam int id) {
        libraryLoanSystemService.returnBook(id);
        return ResponseEntity.noContent().build();
    }

}
