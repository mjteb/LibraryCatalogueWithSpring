package com.lb.librarycatalogue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.mapper.LibraryMemberMapper;
import com.lb.librarycatalogue.mapper.pojos.LibraryMemberDto;
import com.lb.librarycatalogue.service.LibraryMemberService;
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

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LibraryMemberController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LibraryMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryMemberService libraryMemberService;

    @MockBean
    private LibraryMemberMapper libraryMemberMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private LibraryMemberDto libraryMemberDto;
    private LibraryMemberEntity libraryMemberEntity;


    @BeforeEach
    public void init() {
        libraryMemberDto = LibraryMemberDto.builder().
                firstName("John")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(2015, 12, 31))
                .membershipExpirationDate(LocalDate.now().plusYears(2))
                .cardNumber("SMITKAY19500401")
                .phoneNumber("514-656-6565")
                .build();

        libraryMemberEntity = LibraryMemberEntity.builder()
                .firstName("Kayla")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(2015, 12, 31))
                .membershipExpirationDate(LocalDate.now().plusYears(2))
                .phoneNumber("1231235454")
                .cardNumber("SMITKAY19500401")
                .build();
    }

    @Test
    public void givenBarcode_whenAddLibraryMember_thenNothingReturned() throws Exception {
        doNothing().when(libraryMemberService).addLibraryMember(libraryMemberEntity);

        ResultActions response = mockMvc.perform(post("/librarymembermanagement/librarymember")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libraryMemberDto)));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void givenNothing_whenDeleteLibraryMember_thenNothingReturned() throws Exception {
        String cardNumber = "SMITKAY19500401";
        doNothing().when(libraryMemberService).deleteLibraryMember(cardNumber);

        ResultActions response = mockMvc.perform(delete("/librarymembermanagement/librarymember")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cardNumber", "SMITKAY19500401"));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void givenCardNumber_whenGetLibraryMember_thenMemberReturned() throws Exception {
        when(libraryMemberService.getLibraryMember("SMITKAY19500401")).thenReturn(libraryMemberEntity);
        when(libraryMemberMapper.mapLibraryMemberEntityToDto(libraryMemberEntity)).thenReturn(libraryMemberDto);

        ResultActions response = mockMvc.perform(get("/librarymembermanagement/librarymember")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libraryMemberDto))
                .param("cardNumber", "SMITKAY19500401"));

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(libraryMemberDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(libraryMemberDto.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(libraryMemberDto.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber", CoreMatchers.is(libraryMemberDto.getCardNumber())));

    }

    @Test
    public void givenCardNumberFirstNameAndPhoneNumber_whenModifyLibraryMember_thenNothingReturned() throws Exception {
        String cardNumber = "SMITKAY19500401";
        String firstName = "Tom";
        String phoneNumber = "123-123-1212";
        doNothing().when(libraryMemberService).modifyLibraryMember(cardNumber, firstName, null, phoneNumber);

        ResultActions response = mockMvc.perform(put("/librarymembermanagement/librarymember")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cardNumber", "SMITKAY19500401")
                .param("firstName", firstName)
                .param("phoneNumber", phoneNumber));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void givenCardNumber_whenRenewMembership_thenNothingReturned() throws Exception {
        String cardNumber = "SMITKAY19500401";
        doNothing().when(libraryMemberService).renewMembership(cardNumber);

        ResultActions response = mockMvc.perform(put("/librarymembermanagement/librarymemberrenewal")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cardNumber", "SMITKAY19500401"));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void givenCardNumberAndAmountPaid_whenPayLibraryFines_thenNothingReturned() throws Exception {
        String cardNumber = "SMITKAY19500401";
        double amountPaid = 20.00;
        doNothing().when(libraryMemberService).payLibraryFines(cardNumber, amountPaid);

        ResultActions response = mockMvc.perform(put("/librarymembermanagement/paylibraryfees")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cardNumber", cardNumber)
                .param("amountPaid", String.valueOf(amountPaid)));

        response.andExpect(status().isNoContent());
    }

}