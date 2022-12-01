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



}
