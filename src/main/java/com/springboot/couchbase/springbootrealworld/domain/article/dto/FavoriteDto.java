package com.springboot.couchbase.springbootrealworld.domain.article.dto;


import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class FavoriteDto {
    private String id;
    private String body;
    private ProfileDto author;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingleFavorite {
        FavoriteDto favorite;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MultipleFavorites {
        List<FavoriteDto> favorites;
    }
}
