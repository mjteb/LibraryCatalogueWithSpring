package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.repository.BookReservationRepository;
import com.lb.librarycatalogue.repository.BooksRepository;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import com.lb.librarycatalogue.repository.ReservationsAvailableForPickUpRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class BookReservationServiceTest {

    @InjectMocks
    private BookReservationService bookReservationService;

    @Mock
    private BookReservationRepository bookReservationRepository;
    @Mock
    private BooksRepository booksRepository;
    @Mock
    private LibraryMemberRepository libraryMemberRepository;
    @Mock
    private ReservationsAvailableForPickUpRepository reservationsAvailableForPickUpRepository;
    @Mock
    private CopiesOfBooksService copiesOfBooksService;

    @Test
    public void givenId_whenDeleteReservation_ThenCallBookReservationRepository() {
        //arrange
        int id = 2;
        ReservedBooksEntity response = new ReservedBooksEntity();
//        BDDMockito.given(bookReservationRepository.findById(id)).willReturn(Optional.of(new ReservedBooksEntity()));

        //act
        bookReservationService.deleteReservation(id);

        //assert
        verify(bookReservationRepository, times(1)).deleteBy(id);
        verify(booksRepository, times(1)).flush();
    }

}