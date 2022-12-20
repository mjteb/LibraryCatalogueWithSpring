package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.pojos.BooksDto;
import com.lb.librarycatalogue.service.CopiesOfBooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/copiesOfBooks")
public class CopiesOfBooksController {

    private final CopiesOfBooksService copiesOfBooksService;

    public CopiesOfBooksController(CopiesOfBooksService copiesOfBooksService) {

        this.copiesOfBooksService = copiesOfBooksService;
    }


    @PostMapping(value ="/deletecopy")
    ResponseEntity<Void> deleteCopy (String barcode) {
        copiesOfBooksService.deleteCopy(barcode);
        return ResponseEntity.noContent().build();
    }


}
