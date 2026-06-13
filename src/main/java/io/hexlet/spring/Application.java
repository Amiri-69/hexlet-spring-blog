package io.hexlet.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@RequestMapping("/api")
public class Application {

    @Value("${app.welcome-message}")
    private String welcomeMessage;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Добро пожаловать в Hexlet Spring Blog!";
    }

    @GetMapping("/about")
    public String about() {
        return "This is simple Spring blog!";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return welcomeMessage;
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }
}

