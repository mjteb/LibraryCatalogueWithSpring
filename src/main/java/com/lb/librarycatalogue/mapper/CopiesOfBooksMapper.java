package com.lb.librarycatalogue.mapper;

import com.lb.librarycatalogue.entity.CopiesOfBooksEntity;
import com.lb.librarycatalogue.mapper.pojos.CopiesOfBooksDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CopiesOfBooksMapper {

    CopiesOfBooksEntity mapCopiesOfBooksDtoToEntity(CopiesOfBooksDto copiesOfBooksDto);

    List<CopiesOfBooksEntity> mapCopiesOfBooksDtoToEntity(List<CopiesOfBooksDto> copiesOfBooksDtos);

    @InheritInverseConfiguration
    CopiesOfBooksDto mapCopiesOfBooksEntityToDto(CopiesOfBooksEntity copiesOfBooksEntity);

    List<CopiesOfBooksDto> mapCopiesOfBooksEntityToDto(List<CopiesOfBooksEntity> copiesOfBooksEntities);

}
