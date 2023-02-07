package com.springboot.couchbase.springbootrealworld.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthUserDetails implements UserDetails {
    private final Long id;
    private final String email;

    private final String bio;

    @Builder
    public AuthUserDetails(Long id, String email, String bio) {
        this.id = id;
        this.email = email;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // no authority in this project
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
