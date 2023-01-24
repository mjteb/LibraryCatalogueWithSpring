package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.BooksMapper;
import com.lb.librarycatalogue.mapper.pojos.BooksDto;
import com.lb.librarycatalogue.service.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookmanagementsystemforbooks")
public class BooksController {

    private final BooksService booksService;
    private final BooksMapper booksMapper;

    public BooksController(BooksService booksService, BooksMapper booksMapper) {
        this.booksService = booksService;
        this.booksMapper = booksMapper;
    }

    @PostMapping(value = "/addbook")
    public ResponseEntity<Void> addBook(@RequestBody BooksDto booksDto) {
        booksService.addBook(booksMapper.mapBooksDtoToEntity(booksDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/deletebook")
    ResponseEntity<Void> deleteBook(@RequestParam String isbn) {
        booksService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/getbook")
    ResponseEntity<List<BooksDto>> getBook(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {
        return ResponseEntity.ok(booksMapper.mapBooksEntityToDto(booksService.getBook(title, author)));

    }

    @PutMapping(value = "/modifybook")
    ResponseEntity<Void> modifybook(@RequestBody BooksDto booksDto) {
        booksService.modifyBook(booksMapper.mapBooksDtoToEntity(booksDto));
        return ResponseEntity.noContent().build();
    }
}
