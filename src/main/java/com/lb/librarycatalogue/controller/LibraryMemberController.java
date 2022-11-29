package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.mapper.LibraryMemberMapper;
import com.lb.librarycatalogue.mapper.pojos.LibraryMemberDto;
import com.lb.librarycatalogue.service.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/librarymembermanagement")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;
    private final LibraryMemberMapper libraryMemberMapper;

    public LibraryMemberController(LibraryMemberService libraryMemberService, LibraryMemberMapper libraryMemberMapper) {
        this.libraryMemberService = libraryMemberService;
        this.libraryMemberMapper = libraryMemberMapper;
    }

    @PostMapping(value = "/librarymember")
    public ResponseEntity<Void> addLibraryMember (@RequestBody LibraryMemberDto libraryMemberDto) {
        libraryMemberService.addLibraryMember(libraryMemberMapper.mapLibraryMemberDtoToEntity(libraryMemberDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/librarymember")
    public ResponseEntity<Void> deleteLibraryMember (@RequestParam String cardNumber) {
        libraryMemberService.deleteLibraryMember(cardNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/librarymember")
    public ResponseEntity<LibraryMemberDto> getLibraryMember(@RequestParam String cardNumber) {
//        return ResponseEntity.ok(libraryMemberService.getLibraryMember(cardNumber));
        return ResponseEntity.ok(libraryMemberMapper.mapLibraryMemberEntityToDto(libraryMemberService.getLibraryMember(cardNumber)));
    }

    @PutMapping(value = "/librarymember")
    public ResponseEntity<Void> modifyLibraryMember (
            @RequestParam String cardNumber,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNumber
            ) {
        libraryMemberService.modifyLibraryMember(cardNumber, firstName, lastName, phoneNumber);
        return ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/librarymemberrenewal")
    public ResponseEntity<Void> renewMembership (@RequestParam String cardNumber) {
        libraryMemberService.renewMembership(cardNumber);
        return ResponseEntity.noContent().build();

    }

//    @GetMapping(value = "/recipe")
//    public ResponseEntity<List<PersonalRecipesEntity>> getRecipes(
//            @RequestParam(name = "typeOfRecipe", required = false) String typePersonalRecipesEntityFilter,
//            @RequestParam(name = "nameOfRecipe", required = false) String personalRecipeNameFilter,
//            @RequestParam(name = "ingredientFilter", required = false) List<String> ingredientFilter
//    ) {
//        return ResponseEntity.ok(personalRecipeManagementService.getPersonalRecipes(typePersonalRecipesEntityFilter, personalRecipeNameFilter, ingredientFilter));
//    }

//    @DeleteMapping(value = "/personalrecipe")
//    public ResponseEntity<Void> deletePersonalRecipe(@RequestParam String name) {
//        personalRecipeManagementService.deletePersonalRecipe(name);
//        return ResponseEntity.noContent().build();
//    }
//    public ResponseEntity<Void> addPersonalRecipe(@RequestBody PersonalRecipesEntity personalRecipesEntity) {
//        personalRecipeManagementService.addNewPersonalRecipe(personalRecipesEntity);
//        return ResponseEntity.noContent().build();
//    }
}
