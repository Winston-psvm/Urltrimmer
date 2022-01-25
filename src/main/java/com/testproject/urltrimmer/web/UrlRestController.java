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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
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

    @Transactional
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirectUrl(@PathVariable String shortUrl){
        Optional<ShortUrl> optionalShortUrl = repository.findByShortUrl(PATH + shortUrl);

        if (optionalShortUrl.isEmpty()) throw new IllegalRequestDataException("Link not found.");
            ShortUrl url = optionalShortUrl.get();
        if (checkDate(url.getEndDate())) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", url.getFullUrl());
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            deleteDeadLink(url.getId());
            throw new IllegalRequestDataException("The life of your link is over.");
        }
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
                (null, url.getFullUrl() , PATH + hash , null , url.getEndDate()));

        else return repository.save(new ShortUrl
                (null, url.getFullUrl() , PATH + hash , authUser.id(), url.getEndDate()));
    }

    private void deleteDeadLink(Integer id){
        repository.delete(id);
    }

    private boolean checkDate(LocalDate endDate){
        if (ZonedDateTime.now().toLocalDate().isEqual(endDate)) return true;
        return ZonedDateTime.now().toLocalDate().isBefore(endDate);
        }
}
