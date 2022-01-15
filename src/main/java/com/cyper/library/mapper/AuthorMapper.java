package com.cyper.library.mapper;

import com.cyper.library.dto.CreateAuthorDto;
import com.cyper.library.model.Author;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void mapAuthorFromCreateAuthorDto(CreateAuthorDto dto, @MappingTarget Author author);

}
