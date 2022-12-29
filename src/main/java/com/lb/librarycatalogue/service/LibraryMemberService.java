package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryMemberService {

    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public void addLibraryMember(LibraryMemberEntity libraryMemberEntity) {

        LocalDate expirationDate = LocalDate.now().plusYears(2);
        String cardNumber = libraryMemberEntity.getLastName().substring(0, 4).toUpperCase().concat(libraryMemberEntity.getFirstName().substring(0, 3).toUpperCase()).concat(libraryMemberEntity.getDateOfBirth().toString().replaceAll("-", ""));
        LibraryMemberEntity libraryMember = LibraryMemberEntity.builder()
                .firstName(libraryMemberEntity.getFirstName())
                .lastName(libraryMemberEntity.getLastName())
                .dateOfBirth(libraryMemberEntity.getDateOfBirth())
                .membershipExpirationDate(expirationDate)
                .phoneNumber(libraryMemberEntity.getPhoneNumber())
                .cardNumber(cardNumber)
                .build();
        libraryMemberRepository.save(libraryMember);
    }


    public void deleteLibraryMember(String cardNumber) {
        LibraryMemberEntity libraryMember = libraryMemberRepository
                .findById(cardNumber)
                .orElseThrow(() -> {
            throw new RuntimeException("Member does not exist");
        });
        libraryMemberRepository.delete(libraryMember);
    }


    public LibraryMemberEntity getLibraryMember(String cardNumber) {
        if (libraryMemberRepository.existsById(cardNumber)) {
            return libraryMemberRepository.findById(cardNumber).get();
        }
        else {
            throw new RuntimeException("Member does not exist");
        }

    }

    public void modifyLibraryMember(String cardNumber, String firstName, String lastName, String phoneNumber) {
        LibraryMemberEntity originalLibraryMember = libraryMemberRepository.findById(cardNumber).orElseThrow(() -> {
            throw new RuntimeException("Member does not exist");
        });
        LibraryMemberEntity updatedLibraryMember = LibraryMemberEntity.builder()
                .firstName(firstName != null ? firstName : originalLibraryMember.getFirstName())
                .lastName(lastName != null ? lastName : originalLibraryMember.getLastName())
                .dateOfBirth(originalLibraryMember.getDateOfBirth())
                .membershipExpirationDate(originalLibraryMember.getMembershipExpirationDate())
                .phoneNumber(phoneNumber != null ? phoneNumber : originalLibraryMember.getPhoneNumber())
                .cardNumber(originalLibraryMember.getCardNumber())
                .build();

        libraryMemberRepository.save(updatedLibraryMember);
    }

    public void renewMembership(String cardNumber) {
        libraryMemberRepository.findById(cardNumber).orElseThrow(() -> {
            throw new RuntimeException("Member does not exist");
        });
        LocalDate newExpirationDate = LocalDate.now().plusYears(2);
        libraryMemberRepository.renewMembership(cardNumber, newExpirationDate);
    }

    public void payLibraryFines(String cardNumber, double amountPaid) {
        LibraryMemberEntity member = libraryMemberRepository.findById(cardNumber).get();
        if (amountPaid <= member.getTotalLibraryFees()) {
            member.setLibraryFeesFromBooksReturned(member.getLibraryFeesFromBooksReturned() - amountPaid);
            member.setTotalLibraryFees(member.getLibraryFeesFromBooksReturned() + member.getLibraryFeesFromBooksCurrentlyBorrowed());
            libraryMemberRepository.save(member);
        }
        else {
            throw new RuntimeException("Amount paid is greater than total library fees");
        }
    }
}

