package io.hexlet.spring.mapper;

import io.hexlet.spring.dto.PostPatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.hexlet.spring.dto.PostCreateDTO;
import io.hexlet.spring.dto.PostUpdateDTO;
import io.hexlet.spring.dto.PostDTO;
import io.hexlet.spring.model.Post;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE
)
public interface PostMapper {

    void updateFromPatch(
            PostPatchDTO dto,
            @MappingTarget Post post
    );

    @Mapping(target = "userId", source = "user.id")
    PostDTO toDTO(Post post);

    Post toEntity(PostCreateDTO dto);

    void updateEntityFromDTO(
            PostUpdateDTO dto,
            @MappingTarget Post post
    );
}