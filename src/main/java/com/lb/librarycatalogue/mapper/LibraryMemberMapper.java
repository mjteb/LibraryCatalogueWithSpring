package com.lb.librarycatalogue.mapper;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.mapper.pojos.LibraryMemberDto;
import org.mapstruct.*;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LibraryMemberMapper {

    LibraryMemberEntity mapLibraryMemberDtoToEntity(LibraryMemberDto libraryMemberDto);
    List<LibraryMemberEntity> mapLibraryMemberDtoToEntity(List<LibraryMemberDto> libraryMemberDto);

    @InheritInverseConfiguration
    LibraryMemberDto mapLibraryMemberEntityToDto(LibraryMemberEntity libraryMemberEntity);
    List<LibraryMemberDto> mapLibraryMemberEntityToDto(List<LibraryMemberEntity> libraryMemberEntity);


    //    @AfterMapping
//    default void pouet(@MappingTarget LibraryMemberEntity libraryMemberEntity, LibraryMemberDto libraryMemberDto) {
//        if (Date.from(Instant.now()).after(Date.valueOf(libraryMemberEntity.getMembershipExpirationDate()))) {
//            libraryMemberEntity.setCardNumber(null);
//        }
//    }


//    @Mapping(source = "cell", target = "phoneNumber")
//            qualifiedByName = "nomMethode")

//    @Named("nomMethode")
//    default String mapPhoneNumber(LibraryMemberDto libraryMemberDto) {
//        return libraryMemberDto.getCell();
//    }
}
