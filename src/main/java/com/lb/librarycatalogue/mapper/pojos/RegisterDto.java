package com.lb.librarycatalogue.mapper.pojos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String cardNumber;
    private LocalDate dateOfBirth;
}
