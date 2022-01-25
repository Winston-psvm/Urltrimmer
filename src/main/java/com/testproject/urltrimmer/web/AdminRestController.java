package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.Role;
import com.testproject.urltrimmer.model.ShortUrl;
import com.testproject.urltrimmer.model.User;
import com.testproject.urltrimmer.repository.JpaUrlRepository;
import com.testproject.urltrimmer.repository.JpaUserRepository;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.testproject.urltrimmer.util.UserUtil.prepareToSave;

@RestController
@RequestMapping(value = "/UrlTrimmer/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {
    private final JpaUserRepository userRepository;
    private final JpaUrlRepository urlRepository;

    public AdminRestController(JpaUserRepository userRepository, JpaUrlRepository urlRepository) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @PutMapping("/users/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAdmin(@PathVariable String email) {
        Optional<User> optUser = userRepository.getByEmail(email);
        if (optUser.isEmpty()) throw new IllegalRequestDataException("User not found.");
        User newAdmin = optUser.get();
        newAdmin.setRoles(Collections.singleton(Role.ADMIN));

        userRepository.save(prepareToSave(newAdmin));
    }

    @GetMapping("/urls")
    public List<ShortUrl> getUrls(){
        return urlRepository.findAll();
    }

    @DeleteMapping("/urls/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        urlRepository.delete(id);
    }



}
