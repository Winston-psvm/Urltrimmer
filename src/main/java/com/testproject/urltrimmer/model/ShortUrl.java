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
import java.time.LocalDate;


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

    @Column(name = "end_date")
    private LocalDate endDate;

    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Integer userId;

    @Column(name = "counter")
    private Integer counter;

    public ShortUrl(Integer id, String fullUrl, String shortUrl, Integer userId, LocalDate endDate) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.userId = userId;
        this.endDate = endDate;
        this.counter = 0;
    }

    public ShortUrl() {}

    public Integer getCounter() {
        return counter;
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

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
