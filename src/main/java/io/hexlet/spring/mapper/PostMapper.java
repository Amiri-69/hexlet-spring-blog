package io.hexlet.spring.mapper;

import io.hexlet.spring.dto.PostPatchDTO;
import io.hexlet.spring.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.hexlet.spring.dto.PostCreateDTO;
import io.hexlet.spring.dto.PostUpdateDTO;
import io.hexlet.spring.dto.PostDTO;
import io.hexlet.spring.model.Post;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;
import java.util.stream.Collectors;

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
    @Mapping(target = "tagIds", source = "tags")
    PostDTO toDTO(Post post);

    Post toEntity(PostCreateDTO dto);

    void updateEntityFromDTO(
            PostUpdateDTO dto,
            @MappingTarget Post post
    );
    default Set<Long> mapTags(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());
    }
}