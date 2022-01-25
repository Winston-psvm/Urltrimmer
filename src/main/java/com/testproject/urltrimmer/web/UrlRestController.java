package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.ShortUrl;
import com.testproject.urltrimmer.repository.JpaUrlRepository;
import com.testproject.urltrimmer.util.CodeGenerator;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@RequestMapping(value = "/UrlTrimmer", produces = MediaType.APPLICATION_JSON_VALUE)
public class UrlRestController {

    private final JpaUrlRepository repository;
    private final CodeGenerator generator;
    private static final String PATH = "http://localhost:8080/UrlTrimmer/";

    public UrlRestController(JpaUrlRepository repository, CodeGenerator generator) {
        this.repository = repository;
        this.generator = generator;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirectUrl(@PathVariable String shortUrl){
        Optional<ShortUrl> optionalShortUrl = repository.findByShortUrl(PATH + shortUrl);
        return optionalShortUrl.map(url -> ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION,
                        url.getFullUrl()).build()).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShortUrl createShortUrl(@RequestBody ShortUrl url, @AuthenticationPrincipal AuthUser authUser){

        Optional<ShortUrl> optShort = repository.findByFullUrl(url.getFullUrl());
        if (optShort.isPresent()) return optShort.get();

        String hash = generator.generate(url.getFullUrl().length());
        Optional<ShortUrl> optionalShortUrl = repository.findByShortUrl(PATH + hash);
        if (optionalShortUrl.isPresent()) throw new IllegalRequestDataException("Oops. Something went wrong.");

        Optional<AuthUser> optional = Optional.ofNullable(authUser);
        if (optional.isEmpty())
        return repository.save(new ShortUrl
                (null, url.getFullUrl() , PATH + hash , null ,
                        ZonedDateTime.now().toLocalDateTime()));

        else return repository.save(new ShortUrl
                (null, url.getFullUrl() , PATH + hash , authUser.id() ,
                        ZonedDateTime.now().toLocalDateTime()));
    }
}
