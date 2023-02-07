package com.springboot.couchbase.springbootrealworld.domain.tag.dto;

import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class TagDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagList {
        List<String> tags;
    }




}


