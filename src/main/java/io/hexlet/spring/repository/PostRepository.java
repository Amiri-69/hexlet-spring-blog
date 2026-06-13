package io.hexlet.spring.repository;

import io.hexlet.spring.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByPublishedTrue(Pageable pageable);
}