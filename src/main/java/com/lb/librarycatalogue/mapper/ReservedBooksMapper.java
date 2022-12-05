package com.lb.librarycatalogue.mapper;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.entity.ReservedBooksEntity;
import com.lb.librarycatalogue.mapper.pojos.BooksDto;
import com.lb.librarycatalogue.mapper.pojos.LibraryMemberDto;
import com.lb.librarycatalogue.mapper.pojos.ReservedBooksDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservedBooksMapper {

    ReservedBooksEntity mapReservedBooksDtoToEntity(ReservedBooksDto reservedBooksDto);
    List<ReservedBooksEntity> mapReservedBooksDtoToEntity(List<ReservedBooksDto> reservedBooksDtos);


    @InheritInverseConfiguration
    ReservedBooksDto mapReservedBooksEntityToDto(ReservedBooksEntity reservedBooksEntity);
    List<ReservedBooksDto> mapReservedBooksEntityToDto(List<ReservedBooksEntity> reservedBooksEntities);



}
