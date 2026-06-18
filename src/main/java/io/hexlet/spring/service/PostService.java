package io.hexlet.spring.service;

import io.hexlet.spring.dto.*;
import io.hexlet.spring.mapper.PostMapper;
import io.hexlet.spring.model.Post;
import io.hexlet.spring.model.Tag;
import io.hexlet.spring.model.User;
import io.hexlet.spring.repository.PostRepository;
import io.hexlet.spring.repository.TagRepository;
import io.hexlet.spring.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import io.hexlet.spring.dto.PostParamsDTO;
import io.hexlet.spring.specification.PostSpecification;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostSpecification postSpecification;

    public PostService(
            PostRepository postRepository,
            PostMapper postMapper,
            UserRepository userRepository,
            TagRepository tagRepository,
            PostSpecification postSpecification
    ) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.postSpecification = postSpecification;
    }

    public PostDTO findById(Long id) {

        var post = postRepository.findById(id)
                .orElseThrow();

        return postMapper.toDTO(post);
    }

    public PostDTO create(
            PostCreateDTO dto
    ) {

        User user = userRepository
                .findById(dto.getUserId())
                .orElseThrow();

        Post post = postMapper.toEntity(dto);

        post.setUser(user);

        Set<Tag> tags =
                dto.getTagIds()
                        .stream()
                        .map(tagId ->
                                tagRepository
                                        .findById(tagId)
                                        .orElseThrow()
                        )
                        .collect(Collectors.toSet());

        post.setTags(tags);

        postRepository.save(post);

        return postMapper.toDTO(post);
    }

    public PostDTO update(
            Long id,
            PostUpdateDTO dto
    ) {

        Post post = postRepository
                .findById(id)
                .orElseThrow();

        postMapper.updateEntityFromDTO(
                dto,
                post
        );

        User user = userRepository
                .findById(dto.getUserId())
                .orElseThrow();

        post.setUser(user);

        Set<Tag> tags =
                dto.getTagIds()
                        .stream()
                        .map(tagId ->
                                tagRepository
                                        .findById(tagId)
                                        .orElseThrow()
                        )
                        .collect(Collectors.toSet());

        post.setTags(tags);

        postRepository.save(post);

        return postMapper.toDTO(post);
    }

    public void delete(Long id) {

        postRepository.deleteById(id);
    }

    public PostDTO patch(
            Long id,
            PostPatchDTO dto
    ) {

        Post post = postRepository.findById(id)
                .orElseThrow();

        postMapper.updateFromPatch(
                dto,
                post
        );

        Post saved = postRepository.save(post);

        return postMapper.toDTO(saved);
    }

    public Page<PostDTO> getAll(
            PostParamsDTO params,
            int page
    ) {

        var spec = postSpecification.build(params);

        var posts = postRepository.findAll(
                spec,
                PageRequest.of(page - 1, 10)
        );

        return posts.map(postMapper::toDTO);
    }
}

