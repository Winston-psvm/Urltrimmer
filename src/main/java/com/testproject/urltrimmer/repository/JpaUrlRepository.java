package com.testproject.urltrimmer.repository;

import com.testproject.urltrimmer.model.ShortUrl;

import java.util.List;

public interface JpaUrlRepository extends BaseRepository<ShortUrl> {

    List<ShortUrl> getAllByUserId(Integer id);
}
