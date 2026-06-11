package io.hexlet.spring;

import io.hexlet.spring.model.Post;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private List<Post> posts = new ArrayList<>();

    @GetMapping
    public List<Post> index() {
        return posts;
    }

    @GetMapping("/{id}")
    public Post show(@PathVariable int id) {
        return posts.get(id);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        post.setCreatedAt(LocalDateTime.now());
        posts.add(post);
        return post;
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable int id, @RequestBody Post post) {
        posts.set(id, post);
        return post;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        posts.remove(id);
    }
}