package com.testproject.urltrimmer.repository;

import com.testproject.urltrimmer.model.ShortUrl;

import java.util.List;
import java.util.Optional;

public interface JpaUrlRepository extends BaseRepository<ShortUrl> {

    List<ShortUrl> getAllByUserId(Integer id);

    Optional<ShortUrl> findByShortUrl(String shortUrl);

    Optional<ShortUrl> findByFullUrl(String fullUrl);

    ShortUrl getByIdAndUserId(Integer id, Integer userId);

}
