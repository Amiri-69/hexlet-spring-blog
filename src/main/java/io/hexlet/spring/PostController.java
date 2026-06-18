package io.hexlet.spring;

import io.hexlet.spring.dto.PostPatchDTO;
import io.hexlet.spring.dto.PostUpdateDTO;
import io.hexlet.spring.model.Post;
import io.hexlet.spring.model.User;
import io.hexlet.spring.repository.PostRepository;
import io.hexlet.spring.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;
import io.hexlet.spring.dto.PostDTO;
import io.hexlet.spring.mapper.PostMapper;
import io.hexlet.spring.dto.PostCreateDTO;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;


    public PostController(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    // GET all posts
    @GetMapping
    public Page<PostDTO> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        var pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Order.desc("createdAt"))
        );

        return postRepository.findAllByPublishedTrue(pageable)
                .map(postMapper::toDTO);
    }

    // GET post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> show(@PathVariable Long id) {

        return postRepository.findById(id)
                .map(post -> ResponseEntity.ok(
                        postMapper.toDTO(post)
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE post
    @PostMapping
    public ResponseEntity<PostDTO> create(
            @RequestBody PostCreateDTO dto
    ) {

        User user = userRepository
                .findById(dto.getUserId())
                .orElseThrow();

        Post post = postMapper.toEntity(dto);

        post.setUser(user);

        postRepository.save(post);

        return ResponseEntity.ok(
                postMapper.toDTO(post)
        );
    }

    // UPDATE post
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateDTO dto) {

        return postRepository.findById(id)
                .map(post -> {

                    postMapper.updateEntityFromDTO(dto, post);

                    User user = userRepository
                            .findById(dto.getUserId())
                            .orElseThrow();

                    post.setUser(user);

                    Post saved =
                            postRepository.save(post);

                    return ResponseEntity.ok(
                            postMapper.toDTO(saved)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> patch(
            @PathVariable Long id,
            @RequestBody PostPatchDTO dto) {

        return postRepository.findById(id)
                .map(post -> {

                    postMapper.updateFromPatch(
                            dto,
                            post
                    );

                    Post saved =
                            postRepository.save(post);

                    return ResponseEntity.ok(
                            postMapper.toDTO(saved)
                    );
                })
                .orElse(
                        ResponseEntity.notFound()
                                .build()
                );
    }
}