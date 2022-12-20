package com.lb.librarycatalogue.mapper.pojos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRegisterDto {
    private String username;
    private String password;
}
