package com.testproject.urltrimmer.repository;

import com.testproject.urltrimmer.model.ShortUrl;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaUrlRepository extends BaseRepository<ShortUrl> {

    List<ShortUrl> getAllByUserId(Integer id);

    Optional<ShortUrl> findByShortUrl(String shortUrl);

    Optional<ShortUrl> findByFullUrl(String fullUrl);

    ShortUrl getByIdAndUserId(Integer id, Integer userId);

    @Modifying
    @Query("UPDATE ShortUrl s set s.counter = s.counter + 1 where s.id = :id")
    void updateUrlCounter(Integer id);

}
