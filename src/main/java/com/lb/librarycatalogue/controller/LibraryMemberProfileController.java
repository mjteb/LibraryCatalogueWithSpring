package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.LibraryMemberMapper;
import com.lb.librarycatalogue.mapper.pojos.LibraryMemberDto;
import com.lb.librarycatalogue.service.LibraryMemberService;
import com.lb.librarycatalogue.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/librarymemberprofile")
public class LibraryMemberProfileController {

    private final LibraryMemberService libraryMemberService;
    private final LibraryMemberMapper libraryMemberMapper;
    private final UserService userService;

    public LibraryMemberProfileController(LibraryMemberService libraryMemberService, LibraryMemberMapper libraryMemberMapper, UserService userService) {
        this.libraryMemberService = libraryMemberService;
        this.libraryMemberMapper = libraryMemberMapper;

        this.userService = userService;
    }


    @GetMapping(value = "/librarymemberinfo")
    public ResponseEntity<LibraryMemberDto> getLibraryMember() {
        String cardNumber = userService.retrieveUserInfo();
        return ResponseEntity.ok(libraryMemberMapper.mapLibraryMemberEntityToDto(libraryMemberService.getLibraryMember(cardNumber)));

    }

}
