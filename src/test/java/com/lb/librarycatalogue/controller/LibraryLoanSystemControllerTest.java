package com.lb.librarycatalogue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.mapper.BorrowedBooksMapper;
import com.lb.librarycatalogue.mapper.pojos.BorrowedBooksDto;
import com.lb.librarycatalogue.service.LibraryLoanSystemService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LibraryLoanSystemController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LibraryLoanSystemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryLoanSystemService libraryLoanSystemService;

    @MockBean
    private BorrowedBooksMapper borrowedBooksMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private BooksBorrowed borrowedBookEntity;
    private BorrowedBooksDto borrowedBooksDto;



    @BeforeEach
    public void init() {
        borrowedBookEntity = BooksBorrowed.builder()
                .idBook("af7ee5d2-d278-4e8d-bc05-c5481af3d837")
                .idMember("SMITJON19500401")
                .title("Normal People")
                .isbnOfBorrowedBook("9781984822178")
                .build();

        borrowedBooksDto = BorrowedBooksDto.builder()
                .idBook("af7ee5d2-d278-4e8d-bc05-c5481af3d837")
                .idMember("SMITJON19500401")
                .title("Normal People")
                .isbnOfBorrowedBook("9781984822178")
                .build();
    }

    @Test
    public void givenBorrowedBookDto_whenBorrowBook_thenNothingReturned() throws Exception {

        ResultActions response = mockMvc.perform(post("/libraryloansystem/borrowbook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(borrowedBooksDto)));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void givenId_whenReturnBook_thenNothingReturned() throws Exception {
        ResultActions response = mockMvc.perform(delete("/libraryloansystem/returnbook")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"));

        response.andExpect(status().isNoContent());
    }
}