package io.hexlet.spring;

import io.hexlet.spring.model.Post;
import io.hexlet.spring.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // GET all posts
    @GetMapping
    public Page<Post> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        var pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Order.desc("createdAt"))
        );

        return postRepository.findAllByPublishedTrue(pageable);
    }

    // GET post by id
    @GetMapping("/{id}")
    public ResponseEntity<Post> show(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE post
    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postRepository.save(post));
    }

    // UPDATE post
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id,
                                       @RequestBody Post updatedPost) {

        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setPublished(updatedPost.isPublished());

                    return ResponseEntity.ok(postRepository.save(post));
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
}