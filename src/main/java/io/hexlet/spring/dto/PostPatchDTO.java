package io.hexlet.spring.dto;

import org.openapitools.jackson.nullable.JsonNullable;

public class PostPatchDTO {

    private JsonNullable<String> title;

    private JsonNullable<String> content;

    public JsonNullable<String> getTitle() {
        return title;
    }

    public void setTitle(
            JsonNullable<String> title) {
        this.title = title;
    }

    public JsonNullable<String> getContent() {
        return content;
    }

    public void setContent(
            JsonNullable<String> content) {
        this.content = content;
    }
}