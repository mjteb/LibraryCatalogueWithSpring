package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.service.LibraryMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/librarymanagementsystem")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;

    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    @PostMapping(value = "/librarymember")
    public ResponseEntity<Void> addLibraryMember (@RequestBody LibraryMemberEntity libraryMemberEntity) {
        libraryMemberService.addLibraryMember(libraryMemberEntity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/librarymember")
    public ResponseEntity<Void> deleteLibraryMember (@RequestParam String cardNumber) {
        libraryMemberService.deleteLibraryMember(cardNumber);
        return ResponseEntity.noContent().build();
    }


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
