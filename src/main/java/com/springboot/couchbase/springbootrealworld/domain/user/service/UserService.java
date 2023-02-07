package com.springboot.couchbase.springbootrealworld.domain.user.service;


import com.springboot.couchbase.springbootrealworld.domain.user.dto.UserDto;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;

public interface UserService {
    UserDto registration(final UserDto registration);

//    UserDto registration(final UserDto.Registration registration);

    UserDto login(final UserDto.Login login);

    UserDto currentUser(final AuthUserDetails authUserDetails);

//    UserDto currentUser(final AuthUserDetails authUserDetails);

    UserDto update(final UserDto.Update update, final AuthUserDetails authUserDetails);

}
