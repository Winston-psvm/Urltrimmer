package com.testproject.urltrimmer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "url")
public class ShortUrl {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @org.hibernate.validator.constraints.URL
    @Column(name = "full_url", nullable = false, unique = true)
    @NotNull
    private String fullUrl;

    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @Column(name = "user_id")
    private Integer userId;

    public ShortUrl(Integer id, String fullUrl, String shortUrl, Integer userId) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.userId = userId;
    }

    public ShortUrl() {

    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
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
