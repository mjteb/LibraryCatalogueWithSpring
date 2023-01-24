package com.lb.librarycatalogue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.mapper.LibraryMemberMapper;
import com.lb.librarycatalogue.mapper.pojos.LibraryMemberDto;
import com.lb.librarycatalogue.service.LibraryMemberService;
import com.lb.librarycatalogue.service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LibraryMemberProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LibraryMemberProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryMemberService libraryMemberService;

    @MockBean
    private LibraryMemberMapper libraryMemberMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    private LibraryMemberDto libraryMemberDto;
    private LibraryMemberEntity libraryMemberEntity;

    @BeforeEach
    public void init() {
        libraryMemberDto = LibraryMemberDto.builder()
                .firstName("John")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(2015, 12, 31))
                .membershipExpirationDate(LocalDate.now().plusYears(2))
                .cardNumber("SMITKAY19500401")
                .phoneNumber("514-656-6565")
                .build();

        libraryMemberEntity = LibraryMemberEntity.builder()
                .firstName("John")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(2015, 12, 31))
                .membershipExpirationDate(LocalDate.now().plusYears(2))
                .phoneNumber("514-656-6565")
                .cardNumber("SMITKAY19500401")
                .build();
    }

    @Test
    public void givenNothing_whenGetLibraryMember_thenMemberReturned() throws Exception {
        when(userService.retrieveUserInfo()).thenReturn(libraryMemberEntity.getCardNumber());
        when(libraryMemberService.getLibraryMember(libraryMemberEntity.getCardNumber())).thenReturn(libraryMemberEntity);
        when(libraryMemberMapper.mapLibraryMemberEntityToDto(libraryMemberEntity)).thenReturn(libraryMemberDto);

        ResultActions response = mockMvc.perform(get("/librarymemberprofile/librarymemberinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libraryMemberDto)));

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(libraryMemberDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(libraryMemberDto.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(libraryMemberDto.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber", CoreMatchers.is(libraryMemberDto.getCardNumber())));

    }

}