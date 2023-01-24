package com.lb.librarycatalogue.service;
import com.lb.librarycatalogue.entity.UserEntity;
import com.lb.librarycatalogue.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;


//    @Test
//    public void giveNothing_whenRetrieveUserInfo_ThenWorks() {
//        //Arrange
//        UserEntity user = constructUser();
//        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
//
//        //Act
//        String username = userService.retrieveUserInfo();
//
//        //Assert
//        assertEquals(user.getCardNumber(), cardNumber);
//    }

    @Test
    public void givenUserName_whenGetCardNumber_ThenWorks() {
        //Arrange
        UserEntity user = constructUser();
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));

        //Act
        String cardNumber = userService.getCardNumber("testUserName");

        //Assert
        verify(userRepository, times(1)).findByUsername("testUserName");
        assertEquals(user.getCardNumber(), cardNumber);
    }



    private UserEntity constructUser(){
        return UserEntity.builder()
                .username("testUsername")
                .password("abc123")
                .cardNumber("SMITKAY19500401")
                .build();
    }


}