package com.springboot.couchbase.springbootrealworld.domain.user.controller;

import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.user.dto.UserDto;
import com.springboot.couchbase.springbootrealworld.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto.SingleUser<UserDto> registration(@RequestBody UserDto.SingleUser<UserDto> article) {
        return new UserDto.SingleUser<>(userService.registration(article.getUser()));
    }

//    @PostMapping
//    public UserDto registration(@RequestBody UserDto.Registration registration) {
//        return userService.registration(registration);
//    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto.Login login) {
        return userService.login(login);
    }

}

