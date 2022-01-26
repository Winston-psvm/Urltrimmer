package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.LinkingCounter;
import com.testproject.urltrimmer.model.ShortUrl;
import com.testproject.urltrimmer.repository.JpaStatsRepository;
import com.testproject.urltrimmer.repository.JpaUrlRepository;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/UrlTrimmer/api/urls", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Url Controller")
public class UrlRestController {
    private final JpaUrlRepository urlRepository;
    private final JpaStatsRepository statsRepository;

    public UrlRestController(JpaUrlRepository urlRepository, JpaStatsRepository statsRepository) {
        this.urlRepository = urlRepository;
        this.statsRepository = statsRepository;
    }

    @GetMapping
    @Operation(summary = "Returns all registered links of the current user.")
    public List<ShortUrl> getUrls(@AuthenticationPrincipal AuthUser authUser) {
        return urlRepository.getAllByUserId(authUser.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes the selected (by ID) link belonging to that user.")
    public void deleteUrl(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser) {
        urlRepository.delete(checkUrl(id, authUser).getId());
    }

    @GetMapping("/count/{id}")
    @Operation(summary ="Get url counter",
            description = "Returns the number of hits on the given link (by Id). " +
                    "The link must belong to the current user.")
    public Integer getUrlsCounter(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser) {
        return checkUrl(id, authUser).getCounter();
    }

    @GetMapping("/stats/{id}")
    @Operation(summary ="Get list of date and counter ",
            description = "Returns a list of entities that contain" +
                    " information about the number of hits on a link on a certain date.")
    public List<LinkingCounter> getStatsAtUrl(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser) {
        return statsRepository.getAllByUrlIdOrderByDate(checkUrl(id, authUser).getId());
    }

    private ShortUrl checkUrl(Integer id, AuthUser authUser) {
        Optional<ShortUrl> url = Optional.ofNullable(urlRepository.getByIdAndUserId(id, authUser.id()));
        if (url.isPresent()) return url.get();
        else throw new IllegalRequestDataException("You don't have this url.");
    }
}
