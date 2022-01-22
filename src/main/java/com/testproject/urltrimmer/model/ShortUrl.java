package com.testproject.urltrimmer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "url")
public class ShortUrl extends BaseEntity {

    @org.hibernate.validator.constraints.URL
    @Column(name = "full_url", nullable = false, unique = true)
    @NotNull
    private String fullUrl;

    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Integer userId;

    public ShortUrl(Integer id, String fullUrl, String shortUrl, Integer userId) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.userId = userId;
    }

    public ShortUrl() {}

    public Integer getUserId() {
        return userId;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getUrl() {
        return fullUrl;
    }

}
