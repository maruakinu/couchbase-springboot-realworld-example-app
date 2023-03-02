package com.springboot.couchbase.springbootrealworld.domain.user.controller;


import com.springboot.couchbase.springbootrealworld.domain.user.dto.UserDto;
import com.springboot.couchbase.springbootrealworld.domain.user.service.UserService;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto currentUser(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        // TODO: userService.getUser(userId: String) 로 변경
//        AuthUserDetails authDetails = new AuthUserDetails("5c863c84-6860-4d17-b86d-88508d875a26", "marlo@gmail.com", "");
        return userService.currentUser(authUserDetails);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UserDto.Update update, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return userService.update(update, authUserDetails);
    }
}
