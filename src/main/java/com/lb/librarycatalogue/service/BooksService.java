package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.specification.BookSpecifications;
import com.lb.librarycatalogue.specification.BookSpecifications.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class BooksService {

    private final BooksRepository booksRepository;
    static final String STATUS_AVAILABLE = "AVAILABLE";

    BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void addBook(BooksEntity booksEntity) {
        if (booksRepository.findById(booksEntity.getIsbn()).isPresent()) {
            throw new RuntimeException("Book already exists in system");
        } else {
            booksEntity.getCopiesOfBooks().stream().forEach(copy -> {
                copy.setStatus(STATUS_AVAILABLE);
                copy.setIsbnOfTitle(booksEntity.getIsbn());
                copy.setBarcode(UUID.randomUUID().toString());
            });
            booksEntity.setNumberOfCopiesAvailable(booksEntity.getCopiesOfBooks().size());
            booksEntity.setTotalNumberOfCopies(booksEntity.getCopiesOfBooks().size());
            booksRepository.save(booksEntity);
        }
    }


    public void deleteBook(String isbn) {
        BooksEntity book = booksRepository.findById(isbn)
                .orElseThrow(() -> {
            throw new RuntimeException("Book is not in system");
        });
        booksRepository.deleteById(isbn);
    }

    public List<BooksEntity> getBook(String title, String author) {
        if (title != null && author != null) {
            return booksRepository.findAll(where(BookSpecifications.likeAuthor(author))
                    .and(BookSpecifications.likeTitle(title)));
        }
        if (title == null && author != null) {
            return booksRepository.findAll(where(BookSpecifications.likeAuthor(author)));
        }
        if (title != null) {
            return booksRepository.findAll(where(BookSpecifications.likeTitle(title)));
        }
        return booksRepository.findAll();
    }

    public void modifyBook(BooksEntity booksEntity) {
        booksRepository.findById(booksEntity.getIsbn()).orElseThrow(() -> {
            throw new RuntimeException("Book is not in system");
        });
        booksEntity.setNumberOfCopiesAvailable(booksEntity.getCopiesOfBooks().size());
        booksEntity.setTotalNumberOfCopies(booksEntity.getCopiesOfBooks().size());
        booksRepository.save(booksEntity);
    }




}

