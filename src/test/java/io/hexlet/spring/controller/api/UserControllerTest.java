package io.hexlet.spring.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import io.hexlet.spring.repository.UserRepository;
import io.hexlet.spring.model.User;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createUser_returns201_andBody() throws Exception {

        var body = """
                {
                    "name": "John",
                    "email": "john@example.com"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUsers_returns200() throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserById_returns200() throws Exception {

        User user = new User();
        user.setName("Mike");
        user.setEmail("mike" + System.currentTimeMillis() + "@test.com");

        user = userRepository.save(user);

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void updateUser_returns200() throws Exception {

        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");

        user = userRepository.save(user);

        var body = """
        {
            "name": "Mike",
            "email": "mike@test.com"
        }
        """;

        mockMvc.perform(
                        put("/api/users/" + user.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mike"))
                .andExpect(jsonPath("$.email").value("mike@test.com"));
    }
    @Test
    void deleteUser_returns204() throws Exception {

        User user = new User();
        user.setName("John");
        user.setEmail("john-delete@test.com");

        user = userRepository.save(user);

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());
    }
}