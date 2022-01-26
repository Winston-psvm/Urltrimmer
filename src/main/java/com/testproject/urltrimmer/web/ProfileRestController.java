package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.User;
import com.testproject.urltrimmer.repository.JpaUserRepository;
import com.testproject.urltrimmer.to.UserTo;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.testproject.urltrimmer.util.UserUtil.*;
import static com.testproject.urltrimmer.util.ValidationUtil.assureIdConsistent;
import static com.testproject.urltrimmer.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = "/UrlTrimmer/api/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profile Controller")
public class ProfileRestController {

    private final JpaUserRepository userRepository;

    public ProfileRestController(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "User registration",
            description = "This can only be done by an unregistered user.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                              "email": "alex@gmail.com",
                                              "password": "password"
                                            }"""),
                                    schema = @Schema(implementation = UserTo.class)))})
    public void register(@Valid @RequestBody UserTo to) {
        checkNew(to);
        checkEmail(to);

        userRepository.save(prepareToSave(createNewFromTo(to)));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user")
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        userRepository.delete(authUser.id());
    }

    @GetMapping
    @Operation(summary = "Get authorized user",
            responses = {
                    @ApiResponse(description = "The user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)))})
    public User getUser(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @Transactional
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update user", responses = {
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "email": "alexius@gmail.com",
                                      "password": "password"
                                    }"""),
                            schema = @Schema(implementation = UserTo.class)))})
    public void update(@RequestBody @Valid UserTo to, @AuthenticationPrincipal AuthUser authUser) {

        if (!to.getEmail().equals(authUser.getUser().getEmail())) checkEmail(to);
        assureIdConsistent(to, authUser.id());
        User user = authUser.getUser();

        userRepository.save(prepareToSave(updateFromTo(user, to)));
    }

    private void checkEmail(UserTo to) {
        if (userRepository.getByEmail(to.getEmail().toLowerCase()).isPresent())
            throw new IllegalRequestDataException("This email has already existed.");
    }
}
