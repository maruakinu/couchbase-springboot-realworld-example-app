package com.springboot.couchbase.springbootrealworld.domain.article.dto;


import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CommentDto {

    protected String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private ProfileDto author;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingleComment {
        CommentDto comment;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MultipleComments {
        List<CommentDto> comments;
    }
}
