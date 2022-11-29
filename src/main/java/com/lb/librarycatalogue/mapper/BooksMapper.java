package com.lb.librarycatalogue.mapper;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import com.lb.librarycatalogue.mapper.pojos.BooksDto;
import com.lb.librarycatalogue.mapper.pojos.LibraryMemberDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BooksMapper {

    BooksEntity mapBooksDtoToEntity (BooksDto booksDto);
    List<BooksEntity> mapBooksDtoToEntity(List<BooksDto> booksDtos);

    @InheritInverseConfiguration
    BooksDto mapBooksEntityToDto(BooksEntity booksEntity);
    List<BooksDto> mapBooksEntityToDto(List<BooksEntity> booksEntity);

}
