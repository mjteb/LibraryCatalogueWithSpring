package com.lb.librarycatalogue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.mapper.CopiesOfBooksMapper;
import com.lb.librarycatalogue.mapper.pojos.CopiesOfBooksDto;
import com.lb.librarycatalogue.service.CopiesOfBooksService;
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
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CopiesOfBooksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CopiesOfBooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CopiesOfBooksService copiesOfBooksService;

    @MockBean
    private CopiesOfBooksMapper copiesOfBooksMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private CopiesOfBooksDto copiesOfBooksDto;
    private CopiesOfBooksEntity copiesOfBooksEntity;

    @BeforeEach
    public void init() {


    }

    @Test
    public void givenNothing_whenAddLibraryMember_thenNothingReturned() throws Exception {
        String barcode = "af7ee5d2-d278-4e8d-bc05-c5481af3d837";
        doNothing().when(copiesOfBooksService).deleteCopy(barcode);

        ResultActions response = mockMvc.perform(delete("/copiesOfBooks/deletecopy")
                .contentType(MediaType.APPLICATION_JSON)
                .param("barcode", barcode));

        response.andExpect(status().isNoContent());
    }

}