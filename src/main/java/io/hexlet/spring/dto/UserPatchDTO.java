package io.hexlet.spring.dto;

import org.openapitools.jackson.nullable.JsonNullable;

public class UserPatchDTO {

    private JsonNullable<String> name;

    private JsonNullable<String> email;

    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(
            JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<String> getEmail() {
        return email;
    }

    public void setEmail(
            JsonNullable<String> email) {
        this.email = email;
    }
}