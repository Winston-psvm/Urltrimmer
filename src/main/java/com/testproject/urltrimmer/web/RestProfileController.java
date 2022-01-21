package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.User;
import com.testproject.urltrimmer.model.UserTo;
import com.testproject.urltrimmer.repository.JpaUserRepository;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static com.testproject.urltrimmer.util.UserUtil.*;
import static com.testproject.urltrimmer.util.ValidationUtil.assureIdConsistent;
import static com.testproject.urltrimmer.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = "/UrlTrimmer/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProfileController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JpaUserRepository repository;

    public RestProfileController(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserTo to){
        log.info("create user {}", to );
        checkNew(to);
        checkEmail(to);

        repository.save(prepareToSave(createNewFromTo(to)));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser);
        repository.delete(authUser.id());
    }

    @Transactional
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo to, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {}", authUser);

        if (!to.getEmail().equals(authUser.getUser().getEmail())) checkEmail(to);
        assureIdConsistent(to, authUser.id());
        User user = authUser.getUser();

        repository.save(prepareToSave(updateFromTo(user, to)));
    }



    private void checkEmail(UserTo to){
        if (repository.getByEmail(to.getEmail().toLowerCase()).isPresent())
            throw new IllegalRequestDataException("This email has already existed.");
    }
}
