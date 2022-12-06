package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.ReservedBooksMapper;
import com.lb.librarycatalogue.mapper.pojos.ReservedBooksDto;
import com.lb.librarycatalogue.service.BookReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bookreservationsystem")
public class BookReservationController {

private final BookReservationService bookReservationService;
private final ReservedBooksMapper reservedBooksMapper;



    public BookReservationController(BookReservationService bookReservationService, ReservedBooksMapper reservedBooksMapper) {
        this.bookReservationService = bookReservationService;
        this.reservedBooksMapper = reservedBooksMapper;
    }

    @DeleteMapping(value = "/reservebook")
    public ResponseEntity<Void> deleteReservation (@RequestParam int id) {
        bookReservationService.stepForDeletingReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/reservebook")
    public ResponseEntity<Void> reserveBook (@RequestBody ReservedBooksDto reservedBooksDto) {
        bookReservationService.reserveBook(reservedBooksMapper.mapReservedBooksDtoToEntity(reservedBooksDto));
        return ResponseEntity.noContent().build();
    }



}
