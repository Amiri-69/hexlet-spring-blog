package io.hexlet.spring;

import io.hexlet.spring.dto.*;
import io.hexlet.spring.model.Post;
import io.hexlet.spring.model.Tag;
import io.hexlet.spring.model.User;
import io.hexlet.spring.repository.PostRepository;
import io.hexlet.spring.repository.TagRepository;
import io.hexlet.spring.repository.UserRepository;
import io.hexlet.spring.service.PostService;
import io.hexlet.spring.specification.PostSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;
import io.hexlet.spring.mapper.PostMapper;
import io.hexlet.spring.dto.PostPatchDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    @Autowired
    private PostSpecification postSpecification;
    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    // GET all posts
    @GetMapping
    public Page<PostDTO> index(
            PostParamsDTO params,
            @RequestParam(defaultValue = "1")
            int page
    ) {

        return postService.getAll(
                params,
                page
        );
    }
    // GET post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> show(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                postService.findById(id)
        );
    }

    // CREATE post
    @PostMapping
    public ResponseEntity<PostDTO> create(
            @RequestBody PostCreateDTO dto
    ) {

        return ResponseEntity.ok(
                postService.create(dto)
        );
    }

    // UPDATE post
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(
            @PathVariable Long id,
            @RequestBody PostUpdateDTO dto
    ) {

        return ResponseEntity.ok(
                postService.update(id, dto)
        );
    }

    // DELETE post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        postService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> patch(
            @PathVariable Long id,
            @RequestBody PostPatchDTO dto
    ) {

        return ResponseEntity.ok(
                postService.patch(id, dto)
        );
    }
}