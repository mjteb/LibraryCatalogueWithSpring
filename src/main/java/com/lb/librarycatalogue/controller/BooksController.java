package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.mapper.BooksMapper;
import com.lb.librarycatalogue.mapper.pojos.BooksDto;
import com.lb.librarycatalogue.service.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookmanagementsystemforbooks")
public class BooksController {

    private final BooksService booksService;
    private final BooksMapper booksMapper;

    public BooksController(BooksService booksService, BooksMapper booksMapper) {
        this.booksService = booksService;
        this.booksMapper = booksMapper;
    }

    @PostMapping(value = "/addbook")
    public ResponseEntity<Void> addBook(@RequestBody BooksDto booksDto) {
        booksService.addBook(booksMapper.mapBooksDtoToEntity(booksDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/deletebook")
    ResponseEntity<Void> deleteBook(@RequestParam String isbn) {
        booksService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/getbook")
    ResponseEntity<List<BooksDto>> getBook(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {
        return ResponseEntity.ok(booksMapper.mapBooksEntityToDto(booksService.getBook(isbn, title, author)));

    }

    @PutMapping(value = "/modifybook")
    ResponseEntity<Void> modifybook(@RequestBody BooksDto booksDto) {
        booksService.modifyBook(booksMapper.mapBooksDtoToEntity(booksDto));
        return ResponseEntity.noContent().build();
    }
//    @PostMapping(value = "/librarymember")
//    public ResponseEntity<Void> addLibraryMember (@RequestBody LibraryMemberDto libraryMemberDto) {
//        libraryMemberService.addLibraryMember(libraryMemberMapper.mapLibraryMemberDtoToEntity(libraryMemberDto));
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping(value = "/librarymember")
//    public ResponseEntity<Void> deleteLibraryMember (@RequestParam String cardNumber) {
//        libraryMemberService.deleteLibraryMember(cardNumber);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping(value = "/librarymember")
//    public ResponseEntity<LibraryMemberDto> getLibraryMember(@RequestParam String cardNumber) {
////        return ResponseEntity.ok(libraryMemberService.getLibraryMember(cardNumber));
//        return ResponseEntity.ok(libraryMemberMapper.mapLibraryMemberEntityToDto(libraryMemberService.getLibraryMember(cardNumber)));
//    }
//
//    @PutMapping(value = "/librarymember")
//    public ResponseEntity<Void> modifyLibraryMember (
//            @RequestParam String cardNumber,
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) String phoneNumber
//            ) {
//        libraryMemberService.modifyLibraryMember(cardNumber, firstName, lastName, phoneNumber);
//        return ResponseEntity.noContent().build();
//
//    }
//
//    @PutMapping(value = "/librarymemberrenewal")
//    public ResponseEntity<Void> renewMembership (@RequestParam String cardNumber) {
//        libraryMemberService.renewMembership(cardNumber);
//        return ResponseEntity.noContent().build();
//
//    }

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
