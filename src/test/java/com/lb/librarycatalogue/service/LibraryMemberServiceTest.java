package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.repository.LibraryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryMemberServiceTest {

    @InjectMocks
    private LibraryMemberService libraryMemberService;


    @Mock
    private LibraryMemberRepository libraryMemberRepository;


    @Test
    public void givenCreatedMember_whenAddLibraryMember_thenItWorks() {
        // Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();

        // Act
        libraryMemberService.addLibraryMember(member);

        // Assert
        verify(libraryMemberRepository, times(1)).save(any(LibraryMemberEntity.class));
    }


    @Test
    public void givenCardNumber_whenDeleteLibraryMember_thenItWorks() {
        //Arrange
        LibraryMemberEntity libraryMember = constructLibraryMemberEntity();
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(libraryMember));

        //Act
        libraryMemberService.deleteLibraryMember("SMITKAY19500401");

        //Assert
        verify(libraryMemberRepository, times(1)).delete(libraryMember);
    }

    @Test
    public void givenCardNumber_whenDeleteLibraryMember_thenThrowsError() {
        //Arrange
        when(libraryMemberRepository.findById(anyString())).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(RuntimeException.class, () -> libraryMemberService.deleteLibraryMember("SMITKAY19500401"));
    }

    @Test
    public void givenCardNumber_whenGetLibraryMember_ThenReturnLibraryMemberEntity() {
        // Arrange
        LibraryMemberEntity response = constructLibraryMemberEntity();
        given(libraryMemberRepository.existsById(anyString())).willReturn(true);
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(response));

        // Act
        LibraryMemberEntity result = libraryMemberService.getLibraryMember("123");

        // Assert
        assertEquals(response.getCardNumber(), result.getCardNumber());
        Assertions.assertThat(result).isNotNull();

    }

    @Test
    public void givenCardNumber_whenGetLibraryMember_ThenThrowRunTimeException() {
        // Arrange
        given(libraryMemberRepository.existsById(anyString())).willReturn(false);

        // Act and assert
        assertThrows(RuntimeException.class, () -> libraryMemberService.getLibraryMember(""));
    }

    @Test
    public void givenModifiedMember_whenModifyLibraryMember_ThenSavesModifiedVersionInRepository() {
        // Arrange
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(constructLibraryMemberEntity()));

        // Act
        libraryMemberService.modifyLibraryMember("SMITKAY19500401", "Kayla", "Smith", "1231231212");

        // Assert
        verify(libraryMemberRepository, times(1)).save(any(LibraryMemberEntity.class));
    }

    @Test
    public void givenModifiedMember_whenModifyLibraryMember_ThenThrowsError() {
        // Arrange
        when(libraryMemberRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act and assert
        assertThrows(RuntimeException.class, () -> libraryMemberService.modifyLibraryMember("SMITKAY19500401", "Kayla", "Smith", "1231231212"));
    }

    @Test
    public void givenCardNumberAndAmountPaid_whenPayLibraryFines_ThenFindMemberAndUpdateFines() {
        // Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();
        member.setTotalLibraryFees(5.00);
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        libraryMemberService.payLibraryFines("SMITKAY19500401", 2.00);

        verify(libraryMemberRepository, times(1)).save(any(LibraryMemberEntity.class));
    }

    @Test
    public void givenCardNumberAndAmountPaid_whenPayLibraryFines_ThenThrowErrorThatAmountPaidIsTooHigh() {
        // Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();
        member.setTotalLibraryFees(5.00);
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        assertThrows(RuntimeException.class, () -> libraryMemberService.payLibraryFines("SMITKAY19500401", 100.00));
    }

    @Test
    public void givenCardNumber_whenRenewMembership_ThenWorks() {
        //Arrange
        LibraryMemberEntity member = constructLibraryMemberEntity();
        LocalDate newExpirationDate = LocalDate.now().plusYears(2);
        given(libraryMemberRepository.findById(anyString())).willReturn(Optional.of(member));

        //Act
        libraryMemberService.renewMembership("SMITKAY19500401");

        //Verify
        verify(libraryMemberRepository, times(1)).renewMembership("SMITKAY19500401", newExpirationDate);

    }

    @Test
    public void givenCardNumber_whenRenewMembership_ThenThrowsError() {
        //Arrange
        when(libraryMemberRepository.findById(anyString())).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(RuntimeException.class, () -> libraryMemberService.renewMembership("test123"));

    }

    private LibraryMemberEntity constructLibraryMemberEntity() {
        return LibraryMemberEntity.builder()
                .firstName("Kayla")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(2015, 12, 31))
                .membershipExpirationDate(LocalDate.now().plusYears(2))
                .phoneNumber("1231235454")
                .cardNumber("SMITKAY19500401")
                .build();
    }

}