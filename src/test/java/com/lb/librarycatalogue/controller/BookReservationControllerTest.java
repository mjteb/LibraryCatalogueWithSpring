package com.lb.librarycatalogue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.mapper.ReservedBooksMapper;
import com.lb.librarycatalogue.mapper.pojos.BorrowedBooksDto;
import com.lb.librarycatalogue.mapper.pojos.ReservedBooksDto;
import com.lb.librarycatalogue.service.BookReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookReservationService bookReservationService;

    @MockBean
    private ReservedBooksMapper reservedBooksMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private ReservedBooksEntity reservedBooksEntity;
    private ReservedBooksDto reservedBooksDto;

    @BeforeEach
    public void init() {
        reservedBooksDto = ReservedBooksDto.builder()
                .idMember("SMITKAY19500401")
                .titleOfReservedBook("Normal People")
                .isbnOfReservedBook("9781984822178")
                .positionInLineForBook(1)
                .build();

        reservedBooksEntity = ReservedBooksEntity.builder()
                .idMember("SMITKAY19500401")
                .titleOfReservedBook("Normal People")
                .isbnOfReservedBook("9781984822178")
                .positionInLineForBook(1)
                .build();
    }

    @Test
    public void givenId_whenDeleteReservation_thenNothingReturned() throws Exception {
        ResultActions response = mockMvc.perform(delete("/bookreservationsystem/deletereservation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void givenReservedBookDto_whenReserveBook_thenReturnsNothing() throws Exception {
        ResultActions response = mockMvc.perform(post("/bookreservationsystem/reservebook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservedBooksDto)));

        response.andExpect(status().isNoContent());
    }
}