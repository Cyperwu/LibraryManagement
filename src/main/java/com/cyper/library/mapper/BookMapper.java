package com.cyper.library.mapper;

import com.cyper.library.dto.CreateBookDto;
import com.cyper.library.model.Book;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void mapBookFromCreateBookDto(CreateBookDto dto, @MappingTarget Book book);

}
