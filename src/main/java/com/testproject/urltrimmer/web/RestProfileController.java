package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.User;
import com.testproject.urltrimmer.model.UserTo;
import com.testproject.urltrimmer.repository.JpaUserRepository;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.testproject.urltrimmer.util.UserUtil.createNewFromTo;
import static com.testproject.urltrimmer.util.UserUtil.prepareToSave;
import static com.testproject.urltrimmer.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = "/UrlTrimmer/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProfileController {

    private final JpaUserRepository repository;

    public RestProfileController(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserTo to){
        checkNew(to);
        checkEmail(to);

        repository.save(prepareToSave(createNewFromTo(to)));
    }

    private void checkEmail(UserTo to){
        if (repository.getByEmail(to.getEmail()).isPresent())
            throw new IllegalRequestDataException("This email has already existed.");
    }
}
