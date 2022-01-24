package com.testproject.urltrimmer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


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
    @Column(name = "short_url",nullable = false, unique = true)
    private String shortUrl;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Integer userId;

    public ShortUrl(Integer id, String fullUrl, String shortUrl, Integer userId, LocalDateTime dateTime) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.userId = userId;
        this.dateTime = dateTime;
    }

    public ShortUrl() {}

    public LocalDateTime getDateTime() {
        return dateTime;
    }

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
