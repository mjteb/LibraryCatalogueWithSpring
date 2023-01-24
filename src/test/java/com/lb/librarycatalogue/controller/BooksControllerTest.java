package com.lb.librarycatalogue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.mapper.BooksMapper;
import com.lb.librarycatalogue.mapper.pojos.BooksDto;
import com.lb.librarycatalogue.service.BooksService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BooksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    @MockBean
    private BooksMapper booksMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private BooksEntity booksEntity;
    private BooksDto booksDto;

    @BeforeEach
    public void init() {
        List<CopiesOfBooksEntity> copies = new ArrayList<>();
         booksEntity = BooksEntity.builder()
                .author("Sally Rooney")
                .isbn("9781984822178")
                .title("Normal People")
                .totalNumberOfCopies(0)
                .numberOfCopiesAvailable(0)
                .numberOfReservations(0)
                .copiesOfBooks(copies)
                .build();

         booksDto = BooksDto.builder()
                .author("Sally Rooney")
                .isbn("9781984822178")
                .title("Normal People")
                .totalNumberOfCopies(0)
                .numberOfCopiesAvailable(0)
                .numberOfReservations(0)
                .copiesOfBooks(copies)
                .build();
    }

    @Test
    public void givenIsbn_whenDeleteBook_thenNothingReturned() throws Exception {
        String isbn = "9781984822178";

        ResultActions response = mockMvc.perform(delete("/bookmanagementsystemforbooks/deletebook")
                .contentType(MediaType.APPLICATION_JSON)
                .param("isbn", isbn));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void givenBookDto_whenAddBook_thenNothingReturned() throws Exception {
        ResultActions response = mockMvc.perform(post("/bookmanagementsystemforbooks/addbook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booksDto)));

        response.andExpect(status().isNoContent());
    }



    @Test
    public void givenBookDto_whenModifyBook_thenNothingReturned() throws Exception {

        ResultActions response = mockMvc.perform(put("/bookmanagementsystemforbooks/modifybook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booksDto)));

        response.andExpect(status().isNoContent());
    }
}