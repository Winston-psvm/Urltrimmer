package com.testproject.urltrimmer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "counters")
public class LinkingCounter extends BaseEntity {

    @NotNull
    @Column(name = "url_id", nullable = false)
    private Integer urlId;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "counter")
    private Integer counter;

    public LinkingCounter(Integer urlId, LocalDate date, Integer counter) {
        this.urlId = urlId;
        this.date = date;
        this.counter = counter;
    }

    public LinkingCounter() {

    }

    public Integer getUrlId() {
        return urlId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}
