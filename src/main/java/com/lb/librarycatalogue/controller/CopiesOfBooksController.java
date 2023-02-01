package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.CopiesOfBooksMapper;
import com.lb.librarycatalogue.mapper.pojos.CopiesOfBooksDto;
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
    private final CopiesOfBooksMapper copiesOfBooksMapper;

    public CopiesOfBooksController(CopiesOfBooksService copiesOfBooksService, CopiesOfBooksMapper copiesOfBooksMapper) {

        this.copiesOfBooksService = copiesOfBooksService;
        this.copiesOfBooksMapper = copiesOfBooksMapper;
    }

    @PostMapping(value = "/addcopy")
    ResponseEntity<Void> addCopy(CopiesOfBooksDto copiesOfBooksDto) {
        copiesOfBooksService.addCopy(copiesOfBooksMapper.mapCopiesOfBooksDtoToEntity(copiesOfBooksDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/deletecopy")
    ResponseEntity<Void> deleteCopy(String barcode) {
        copiesOfBooksService.deleteCopy(barcode);
        return ResponseEntity.noContent().build();
    }


}
