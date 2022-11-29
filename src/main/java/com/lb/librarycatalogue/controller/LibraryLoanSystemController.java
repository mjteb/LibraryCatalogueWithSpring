package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.BooksMapper;
import com.lb.librarycatalogue.mapper.CopiesOfBooksMapper;
import com.lb.librarycatalogue.mapper.LibraryMemberMapper;
import com.lb.librarycatalogue.mapper.pojos.CopiesOfBooksDto;
import com.lb.librarycatalogue.service.LibraryLoanSystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bookmanagementsystemforbooks")
public class LibraryLoanSystemController {



    private final CopiesOfBooksMapper copiesOfBooksMapper;
    private final BooksMapper booksMapper;
    private final LibraryMemberMapper libraryMemberMapper;
    private final LibraryLoanSystemService libraryLoanSystemService;

    public LibraryLoanSystemController(LibraryMemberMapper libraryMemberMapper, CopiesOfBooksMapper copiesOfBooksMapper, BooksMapper booksMapper, LibraryLoanSystemService libraryLoanSystemService) {
        this.copiesOfBooksMapper = copiesOfBooksMapper;
        this.libraryMemberMapper = libraryMemberMapper;
        this.booksMapper = booksMapper;
        this.libraryLoanSystemService = libraryLoanSystemService;
    }

//    @PutMapping(value = "/borrowBook")
//    ResponseEntity<Void> borrowBook (String cardNumber,
//                                     @RequestBody CopiesOfBooksDto copiesOfBooksDto) {
//        libraryLoanSystemService.borrowBook(cardNumber, copiesOfBooksMapper.mapCopiesOfBooksDtoToEntity(copiesOfBooksDto));
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping(value = "/borrowBook")
    ResponseEntity<Void> borrowBook (String cardNumber,
                                     String barcode) {
        libraryLoanSystemService.borrowBook(cardNumber, barcode);
        return ResponseEntity.noContent().build();
    }
}
