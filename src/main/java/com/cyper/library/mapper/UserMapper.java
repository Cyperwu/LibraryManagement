package com.cyper.library.mapper;

import com.cyper.library.dto.CreateUserDto;
import com.cyper.library.dto.UpdateUserDto;
import com.cyper.library.model.User;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void mapUserFromUpdateUserDto(UpdateUserDto dto, @MappingTarget User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void mapUserFromCreateUserDto(CreateUserDto dto, @MappingTarget User user);
}
