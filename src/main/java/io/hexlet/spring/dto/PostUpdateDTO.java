package io.hexlet.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.Set;

public class PostUpdateDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    @Size(min = 10)
    private String content;

    private Long userId;

    private Set<Long> tagIds;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }
    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }
}