package io.hexlet;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import io.hexlet.spring.model.User;
import io.hexlet.spring.repository.UserRepository;
import io.hexlet.spring.model.Post;
import io.hexlet.spring.repository.PostRepository;

@Component
public class ModelGenerator {

    private final Faker faker;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public ModelGenerator(Faker faker,
                          UserRepository userRepository,
                          PostRepository postRepository) {
        this.faker = faker;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void generateData() {

        for (int i = 0; i < 10; i++) {

            User user = new User();
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());

            userRepository.save(user);

            Post post = new Post();
            post.setTitle(faker.book().title());
            post.setContent(faker.lorem().paragraph());
            post.setPublished(true);

            postRepository.save(post);
        }

        System.out.println("TEST DATA GENERATED");
    }
}