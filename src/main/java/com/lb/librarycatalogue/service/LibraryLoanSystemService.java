package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryLoanSystemRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import com.lb.librarycatalogue.utils.LibraryLoanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LibraryLoanSystemService {

    private final LibraryMemberRepository libraryMemberRepository;
    private final LibraryLoanSystemRepository libraryLoanSystemRepository;
    private final BooksRepository booksRepository;


    public LibraryLoanSystemService(LibraryMemberRepository libraryMemberRepository, LibraryLoanSystemRepository libraryLoanSystemRepository, BooksRepository booksRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.libraryLoanSystemRepository = libraryLoanSystemRepository;
        this.booksRepository = booksRepository;

    }

    @Transactional
    public void borrowBook(String cardNumber, String barcode) {

        CopiesOfBooksEntity originalCopy = libraryLoanSystemRepository.findById(barcode).get();
        CopiesOfBooksEntity updatedCopy = CopiesOfBooksEntity.builder()
                .isbnOfTitle(originalCopy.getIsbnOfTitle())
                .barcode(originalCopy.getBarcode())
                .memberBorrowingDocument(cardNumber)
                .dueDate(LocalDate.now().plusWeeks(3))
                .build();
        libraryLoanSystemRepository.save(updatedCopy);
        String isbn = originalCopy.getIsbnOfTitle();
        LibraryLoanUtils.updateBookRecord(booksRepository, isbn);
        LibraryLoanUtils.updateMemberProfile(libraryMemberRepository, cardNumber, barcode);
    }

//    @Transactional
//    public void borrowBook(String cardNumber, CopiesOfBooksEntity copiesOfBooksEntity) {
//        CopiesOfBooksEntity updatedCopy = CopiesOfBooksEntity.builder()
//                .isbnOfTitle(copiesOfBooksEntity.getIsbnOfTitle())
//                .barcode(copiesOfBooksEntity.getBarcode())
//                .memberBorrowingDocument(cardNumber)
//                .dueDate(LocalDate.now().plusWeeks(3))
//                .build();
//        libraryLoanSystemRepository.save(updatedCopy);
//        updateBookRecord(copiesOfBooksEntity);
//        updateMemberProfile(cardNumber);
//    }



}

