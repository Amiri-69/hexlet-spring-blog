package io.hexlet.spring.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import io.hexlet.spring.model.Post;
import io.hexlet.spring.repository.PostRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listPublished_returns200_andPage() throws Exception {

        mockMvc.perform(get("/api/posts")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
    @Autowired
    private PostRepository postRepository;

    @Test
    void createPost_returns201() throws Exception {

        var body = """
        {
            "title": "Spring Boot",
            "content": "Post content",
            "published": true
        }
        """;

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Spring Boot"));
    }
    @Test
    void getPostById_returns200() throws Exception {

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Content");
        post.setPublished(true);

        post = postRepository.save(post);

        mockMvc.perform(get("/api/posts/" + post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("Test Post"));
    }
    @Test
    void updatePost_returns200() throws Exception {

        Post post = new Post();
        post.setTitle("Old");
        post.setContent("Old content");
        post.setPublished(true);

        post = postRepository.save(post);

        var body = """
        {
            "title": "New",
            "content": "New content",
            "published": true
        }
        """;

        mockMvc.perform(put("/api/posts/" + post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New"));
    }
    @Test
    void deletePost_returns204() throws Exception {

        Post post = new Post();
        post.setTitle("Delete");
        post.setContent("Delete content");
        post.setPublished(true);

        post = postRepository.save(post);

        mockMvc.perform(delete("/api/posts/" + post.getId()))
                .andExpect(status().isNoContent());
    }
}