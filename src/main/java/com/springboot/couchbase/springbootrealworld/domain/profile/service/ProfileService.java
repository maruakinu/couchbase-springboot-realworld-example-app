package com.springboot.couchbase.springbootrealworld.domain.profile.service;


import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;

public interface ProfileService {

    ProfileDto getProfile(final String username, final AuthUserDetails authUserDetails);

    ProfileDto followUser(final String name, final AuthUserDetails authUserDetails);

    ProfileDto unfollowUser(final String name, final AuthUserDetails authUserDetails);

    ProfileDto getProfileByUserId(String userId, AuthUserDetails authUserDetails);


    ProfileDto getProfileByUserIds(String userId);
}
