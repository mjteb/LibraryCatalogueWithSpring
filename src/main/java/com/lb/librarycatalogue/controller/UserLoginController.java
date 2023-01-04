package com.lb.librarycatalogue.controller;


import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.entity.Role;
import com.lb.librarycatalogue.entity.UserEntity;
import com.lb.librarycatalogue.mapper.pojos.AuthResponseDTO;
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
@RequestMapping("/libraryportal/auth")
public class UserLoginController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private LibraryMemberRepository libraryMemberRepository;

    @Autowired
    public UserLoginController(AuthenticationManager authenticationManager, UserRepository userRepository,
                               RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, LibraryMemberRepository libraryMemberRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByCardNumber(registerDto.getCardNumber())) {
            return new ResponseEntity<>("An account has already been created that is linked to your library card", HttpStatus.BAD_REQUEST);
        }
        if (!libraryMemberRepository.existsById(registerDto.getCardNumber())) {
            return new ResponseEntity<>("You have entered an invalid card number", HttpStatus.BAD_REQUEST);
        }
        if (invalidDateOfBirth(registerDto.getCardNumber(), registerDto.getDateOfBirth())) {
            return new ResponseEntity<>("You have entered an invalid date of birth", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setCardNumber(registerDto.getCardNumber());
        Role roles = roleRepository.findByName("user").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Account successfully created", HttpStatus.OK);

    }


    public boolean invalidDateOfBirth(String cardNumber, LocalDate dateOfBirth) {
        LibraryMemberEntity member = libraryMemberRepository.findById(cardNumber).get();
        return !member.getDateOfBirth().equals(dateOfBirth);
    }


}
