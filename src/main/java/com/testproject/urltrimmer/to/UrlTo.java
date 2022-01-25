package com.testproject.urltrimmer.to;

import java.time.LocalDate;

public record UrlTo(String url, LocalDate endDate) {
    @Override
    public String url() {
        return url;
    }

    @Override
    public LocalDate endDate() {
        return endDate;
    }
}
