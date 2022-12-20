package com.lb.librarycatalogue.controller;


import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.entity.Role;
import com.lb.librarycatalogue.entity.UserEntity;
import com.lb.librarycatalogue.mapper.pojos.AuthResponseDTO;
import com.lb.librarycatalogue.mapper.pojos.EmployeeRegisterDto;
import com.lb.librarycatalogue.mapper.pojos.LoginDto;
import com.lb.librarycatalogue.mapper.pojos.RegisterDto;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import com.lb.librarycatalogue.repository.RoleRepository;
import com.lb.librarycatalogue.repository.UserRepository;
import com.lb.librarycatalogue.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;

@RestController
@RequestMapping("/admin/auth")
public class EmployeeAndAdminAuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private LibraryMemberRepository libraryMemberRepository;

    @Autowired
    public EmployeeAndAdminAuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, LibraryMemberRepository libraryMemberRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.libraryMemberRepository = libraryMemberRepository;
    }



    @PostMapping(value = "/registeremployee")
    public ResponseEntity<String> createEmployeeAccount(@RequestBody EmployeeRegisterDto employeeRegisterDto) {

        if (userRepository.existsByUsername(employeeRegisterDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

            UserEntity user = new UserEntity();
            user.setUsername(employeeRegisterDto.getUsername());
            user.setPassword(passwordEncoder.encode((employeeRegisterDto.getPassword())));
            Role roles = roleRepository.findByName("employee").get();
            user.setRoles(Collections.singletonList(roles));

            userRepository.save(user);

            return new ResponseEntity<>("Account successfully created", HttpStatus.OK);

    }

}
