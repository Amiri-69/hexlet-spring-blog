package io.hexlet.spring.specification;

import io.hexlet.spring.dto.PostParamsDTO;
import io.hexlet.spring.model.Post;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PostSpecification {

    public Specification<Post> build(
            PostParamsDTO params
    ) {

        return withUserId(params.getUserId())
                .and(withPublished(params.getPublished()))
                .and(withTitleCont(params.getTitleCont()));
    }

    private Specification<Post> withUserId(Long userId) {

        return (root, query, cb) ->

                userId == null
                        ? cb.conjunction()
                        : cb.equal(
                        root.get("user").get("id"),
                        userId
                );
    }

    private Specification<Post> withPublished(
            Boolean published
    ) {

        return (root, query, cb) ->

                published == null
                        ? cb.conjunction()
                        : cb.equal(
                        root.get("published"),
                        published
                );
    }

    private Specification<Post> withTitleCont(
            String title
    ) {

        return (root, query, cb) ->

                title == null
                        ? cb.conjunction()
                        : cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }
}