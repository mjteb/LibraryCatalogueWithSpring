package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.service.CopiesOfBooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/copiesOfBooks")
public class CopiesOfBooksController {

    private final CopiesOfBooksService copiesOfBooksService;

    public CopiesOfBooksController(CopiesOfBooksService copiesOfBooksService) {

        this.copiesOfBooksService = copiesOfBooksService;
    }


    @DeleteMapping(value = "/deletecopy")
    ResponseEntity<Void> deleteCopy(String barcode) {
        copiesOfBooksService.deleteCopy(barcode);
        return ResponseEntity.noContent().build();
    }


}
