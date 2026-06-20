package io.hexlet.spring;

import io.hexlet.spring.dto.LoginDTO;
import io.hexlet.spring.repository.UserRepository;
import io.hexlet.spring.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public String login(
            @RequestBody LoginDTO dto
    ) {

        var user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow();

        if (!user.getPassword()
                .equals(dto.getPassword())) {

            throw new RuntimeException(
                    "Invalid credentials"
            );
        }

        return jwtService.generateToken(
                user.getEmail()
        );
    }
}