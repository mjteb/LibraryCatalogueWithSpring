package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BooksService {

    private final BooksRepository booksRepository;


    BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void addBook(BooksEntity booksEntity) {
        if (booksRepository.findById(booksEntity.getIsbn()).isPresent()) {
            throw new RuntimeException("Book already exists in system");
        }
        else {
            booksEntity.getCopiesOfBooks().stream().forEach(copy -> {
                copy.setStatus("AVAILABLE");
                copy.setIsbnOfTitle(booksEntity.getIsbn());
                copy.setBarcode(UUID.randomUUID().toString());
            });
            booksEntity.setNumberOfCopiesAvailable(booksEntity.getCopiesOfBooks().size());
            booksEntity.setTotalNumberOfCopies(booksEntity.getCopiesOfBooks().size());
         booksRepository.save(booksEntity);
        }
    }


    public void deleteBook(String isbn) {
        Optional.of(booksRepository.findById(isbn)).orElseThrow(() -> {
            throw new RuntimeException("Book is not in system");
        });
        booksRepository.deleteById(isbn);
    }

    public List<BooksEntity> getBook(String isbn, String title, String author) {
        if (isbn != null && title == null && author == null)
        return booksRepository.getBookbyIsbn(isbn);
        if (isbn == null && title != null && author == null) {
            return booksRepository.getBookbyTitle(title);
        }
        if(isbn == null && title == null && author != null) {
            return booksRepository.getBookAuthor(author);
        }
        return booksRepository.findAll();
    }

    public void modifyBook(BooksEntity booksEntity) {
        Optional.of(booksRepository.findById(booksEntity.getIsbn())).orElseThrow(() -> {
            throw new RuntimeException("Book is not in system");
        });
        booksEntity.setNumberOfCopiesAvailable(booksEntity.getCopiesOfBooks().size());
        booksEntity.setTotalNumberOfCopies(booksEntity.getCopiesOfBooks().size());
        booksRepository.save(booksEntity);
    }

}

