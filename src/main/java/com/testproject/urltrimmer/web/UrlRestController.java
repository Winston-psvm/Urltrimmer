package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.LinkingCounter;
import com.testproject.urltrimmer.model.ShortUrl;
import com.testproject.urltrimmer.repository.JpaStatsRepository;
import com.testproject.urltrimmer.repository.JpaUrlRepository;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/UrlTrimmer/api/urls", produces = MediaType.APPLICATION_JSON_VALUE)
public class UrlRestController {
    private final JpaUrlRepository urlRepository;
    private final JpaStatsRepository statsRepository;

    public UrlRestController(JpaUrlRepository urlRepository, JpaStatsRepository statsRepository) {
        this.urlRepository = urlRepository;
        this.statsRepository = statsRepository;
    }

    @GetMapping
    public List<ShortUrl> getUrls(@AuthenticationPrincipal AuthUser authUser) {
        return urlRepository.getAllByUserId(authUser.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser){
        urlRepository.delete(checkUrl(id, authUser).getId());
    }

    @GetMapping("/count/{id}")
    public Integer getUrlsCounter( @PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser){
        return checkUrl(id, authUser).getCounter();
    }

    @GetMapping("/stats/{id}")
    public List<LinkingCounter> getStatsAtUrl(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser ){
        return statsRepository.getAllByUrlIdOrderByDate(checkUrl(id, authUser).getId());
    }

    private ShortUrl checkUrl(Integer id, AuthUser authUser){
        Optional<ShortUrl> url = Optional.ofNullable(urlRepository.getByIdAndUserId(id, authUser.id()));
        if (url.isPresent()) return url.get();
        else throw new IllegalRequestDataException("You don't have this url.");
    }
}
