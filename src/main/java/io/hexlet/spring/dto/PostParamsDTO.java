package io.hexlet.spring.dto;

public class PostParamsDTO {

    private String titleCont;

    private Long userId;

    private Boolean published;

    public String getTitleCont() {
        return titleCont;
    }

    public void setTitleCont(String titleCont) {
        this.titleCont = titleCont;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}