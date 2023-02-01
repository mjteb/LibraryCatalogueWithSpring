package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.repository.BooksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceTest {

    @InjectMocks
    private BooksService booksService;

    @Mock
    private BooksRepository booksRepository;

    @Test
    public void givenBookEntity_whenAddBook_thenItWorks() {
        //Arrange
        BooksEntity book = constructBooksEntity();
       given(booksRepository.findById(anyString())).willReturn(Optional.empty());

        //Act
        booksService.addBook(book);

        //Assert
       verify(booksRepository, times(1)).save(any(BooksEntity.class));
    }

    @Test
    public void givenBookEntity_whenAddBook_thenThrowsError() {
        //Arrange
        BooksEntity book = constructBooksEntity();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(book));

        //Act and Assert
        assertThrows(RuntimeException.class, () -> booksService.addBook(book));
    }

    @Test
    public void givenIsbn_whenDeleteBook_thenItWorks() {
        //Arrange
        BooksEntity book = constructBooksEntity();
        given(booksRepository.findById(anyString())).willReturn(Optional.of(book));

        //Act
        booksService.deleteBook("9781984822178");

        //Assert
        verify(booksRepository, times(1)).deleteById("9781984822178");
    }

    @Test
    public void givenIsbn_whenDeleteBook_thenThrowsError() {
        //Arrange
        when(booksRepository.findById(anyString())).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(RuntimeException.class, () -> booksService.deleteBook("9781984822178"));
    }

    @Test
    public void givenBookEntity_whenModifyBook_thenThrowsError() {
        //Arrange
        BooksEntity book = constructBooksEntity();
        when(booksRepository.findById(anyString())).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(RuntimeException.class, () -> booksService.modifyBook(book));
    }

    @Test
    public void givenBookEntity_whenModifyBook_thenWorks() {
        //Arrange
        BooksEntity book = constructBooksEntity();
        when(booksRepository.findById(anyString())).thenReturn(Optional.of(book));

        //Act
        booksService.modifyBook(book);

        // Assert
        verify(booksRepository, times(1)).save(book);
    }

    @Test
    public void givenNothing_whenGetBook_thenReturnsAllBooks() {
        //Arrange

        //Act
       List<BooksEntity> allBooks = booksService.getBook(null, null, null);

        // Assert
        verify(booksRepository, times(1)).findAll();
        assertEquals(0, allBooks.size());
    }

    private BooksEntity constructBooksEntity() {
        List<CopiesOfBooksEntity> copies = new ArrayList<>();
        List<ReservedBooksEntity> reservations = new ArrayList<>();
        return BooksEntity.builder()
                .author("Sally Rooney")
                .isbn("9781984822178")
                .title("Normal People")
                .totalNumberOfCopies(0)
                .numberOfCopiesAvailable(0)
                .numberOfReservations(0)
                .copiesOfBooks(copies)
                .reservations(reservations)
                .build();
    }

}