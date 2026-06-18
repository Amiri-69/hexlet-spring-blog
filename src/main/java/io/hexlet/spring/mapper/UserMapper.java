package io.hexlet.spring.mapper;

import io.hexlet.spring.dto.UserPatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import io.hexlet.spring.dto.UserCreateDTO;
import io.hexlet.spring.dto.UserUpdateDTO;
import io.hexlet.spring.dto.UserDTO;
import io.hexlet.spring.model.User;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserCreateDTO dto);

    void updateEntityFromDTO(
            UserUpdateDTO dto,
            @MappingTarget User user
    );
    void updateFromPatch(
            UserPatchDTO dto,
            @MappingTarget User user);
}