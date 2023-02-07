package com.springboot.couchbase.springbootrealworld.domain.article.entity;


import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CommentDocument {
    @Id
    protected Long id;
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

