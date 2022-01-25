package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.User;
import com.testproject.urltrimmer.repository.JpaUserRepository;
import com.testproject.urltrimmer.to.UserTo;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
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
public class ProfileRestController {

    private final JpaUserRepository userRepository;

    public ProfileRestController(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserTo to) {
        checkNew(to);
        checkEmail(to);

        userRepository.save(prepareToSave(createNewFromTo(to)));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        userRepository.delete(authUser.id());
    }

    @GetMapping
    public User getUser(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @Transactional
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
