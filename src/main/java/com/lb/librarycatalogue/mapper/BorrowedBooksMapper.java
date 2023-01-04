package com.lb.librarycatalogue.mapper;

import com.lb.librarycatalogue.entity.BooksBorrowed;
import com.lb.librarycatalogue.mapper.pojos.BorrowedBooksDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BorrowedBooksMapper {

    BooksBorrowed mapBorrowedBooksDtoToEntity(BorrowedBooksDto borrowedBooksDto);

    List<BooksBorrowed> mapBorrowedBooksDtoToEntity(List<BorrowedBooksDto> borrowedBooksDtos);

    @InheritInverseConfiguration
    BorrowedBooksDto mapBorrowedBooksEntityToDto(BooksBorrowed booksBorrowed);

    List<BorrowedBooksDto> mapBorrowedBooksEntityToDto(List<BooksBorrowed> booksBorrowed);

}
