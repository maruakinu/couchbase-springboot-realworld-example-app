package com.springboot.couchbase.springbootrealworld.domain.article.entity;


import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CommentDocument {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    protected String id;
    @Field
    private LocalDateTime createdAt = LocalDateTime.now();
    @Field
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Field
    private String body;
    @Field
    private UserDocument author;
    @Field
    private ArticleDocument article;

}

