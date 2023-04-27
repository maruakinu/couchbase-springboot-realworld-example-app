package com.springboot.couchbase.springbootrealworld.domain.profile.controller;




import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import com.springboot.couchbase.springbootrealworld.domain.profile.service.ProfileService;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfilesController {
    @Autowired
    private final ProfileService profileService;

    //Get profile of the login user using username as parameter
    @GetMapping("/{username}")
    public ProfileDto.Single getProfile(@PathVariable("username") String name, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ProfileDto.Single(profileService.getProfile(name, authUserDetails));
    }

    //following a user
    @PostMapping("/{username}/follow")
    public ProfileDto.Single followUser(@PathVariable("username") String name, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ProfileDto.Single(profileService.followUser(name, authUserDetails));
    }

    //unfollow a user
    @DeleteMapping("/{username}/follow")
    public ProfileDto.Single unfollowUser(@PathVariable("username") String name, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ProfileDto.Single(profileService.unfollowUser(name, authUserDetails));
    }

}
