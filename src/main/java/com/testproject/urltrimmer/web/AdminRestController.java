package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.Role;
import com.testproject.urltrimmer.model.ShortUrl;
import com.testproject.urltrimmer.model.User;
import com.testproject.urltrimmer.repository.JpaUrlRepository;
import com.testproject.urltrimmer.repository.JpaUserRepository;
import com.testproject.urltrimmer.to.UrlTo;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.testproject.urltrimmer.util.UserUtil.prepareToSave;

@RestController
@RequestMapping(value = "/UrlTrimmer/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Controller", description = "The necessary role is admin.")
public class AdminRestController {
    private final JpaUserRepository userRepository;
    private final JpaUrlRepository urlRepository;

    public AdminRestController(JpaUserRepository userRepository, JpaUrlRepository urlRepository) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
    }

    @GetMapping("/users")
    @Operation(summary = "Returns a list of all registered users.")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @PutMapping("/users/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary ="Changes the role of the user to admin.",
            description = "By email.")
    public void registerAdmin(@PathVariable String email) {
        Optional<User> optUser = userRepository.getByEmail(email);
        if (optUser.isEmpty()) throw new IllegalRequestDataException("User not found.");
        User newAdmin = optUser.get();
        newAdmin.setRoles(Collections.singleton(Role.ADMIN));

        userRepository.save(prepareToSave(newAdmin));
    }

    @GetMapping("/urls")
    @Operation(summary = "Returns a list of all registered urls.")
    public List<ShortUrl> getUrls() {
        return urlRepository.findAll();
    }

    @DeleteMapping("/urls/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes url by id.")
    public void delete(@PathVariable Integer id) {
        urlRepository.delete(id);
    }

    @Transactional
    @PutMapping(value = "/urls/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Updating any url by id..", description = "Updates fields shortUrl or endDate.", responses = {
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "url": "http://localhost:8080/UrlTrimmer/kek",
                                      "endDate": "2022-03-22"
                                    }"""),
                            schema = @Schema(implementation = UrlTo.class)))})
    public void updateUrl(@RequestBody @Valid UrlTo to, @PathVariable Integer id) {
        ShortUrl oldUrl = urlRepository.getById(id);

        oldUrl.setShortUrl(to.url());
        oldUrl.setEndDate(to.endDate());

        urlRepository.save(oldUrl);
    }

}
