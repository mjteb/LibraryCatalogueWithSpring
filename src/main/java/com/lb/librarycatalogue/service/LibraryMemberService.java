package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LibraryMemberService {

    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public void addLibraryMember(LibraryMemberEntity libraryMemberEntity) {
//        if (!libraryMemberRepository.existsById(libraryMemberEntity.getCardNumber())) {
//            libraryMemberRepository.save(libraryMemberEntity);
//        }
//        else {
//            throw new RuntimeException("Member already in system");
//        }
        LocalDate currentDate = LocalDate.now();
        LibraryMemberEntity libraryMember = LibraryMemberEntity.builder()
                .firstName(libraryMemberEntity.getFirstName())
                .lastName(libraryMemberEntity.getLastName())
                .dateOfBirth(libraryMemberEntity.getDateOfBirth())
                .membershipExpirationDate(currentDate.plusYears(2))
                .cardNumber()
    }

    public void deleteLibraryMember(String cardNumber) {
        if (libraryMemberRepository.existsById(cardNumber)) {
            libraryMemberRepository.deleteById(cardNumber);
        }
        else {
            throw new RuntimeException("Member does not exist");
        }
    }
}
