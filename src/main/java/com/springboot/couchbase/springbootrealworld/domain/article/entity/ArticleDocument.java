package com.springboot.couchbase.springbootrealworld.domain.article.entity;


import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ArticleDocument {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    protected String id;
    @Field
    private String slug;
    @Field
    private String title;
    @Field
    private String description;
    @Field
    private String body;

    @Field
    private List<String> tagList;

    @Field
    private LocalDateTime createdAt = LocalDateTime.now();

    @Field
    private LocalDateTime updatedAt = LocalDateTime.now();

    private List<FavoriteDocument> favoriteList;

    @Field
    private UserDocument author;

    @Builder
    public ArticleDocument(String id, String slug, String title, String description, String body, List<String> taglist, UserDocument author) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = taglist;
        this.author = author;
    }


}

