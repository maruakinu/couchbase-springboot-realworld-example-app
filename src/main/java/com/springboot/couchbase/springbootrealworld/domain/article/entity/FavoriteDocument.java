package com.springboot.couchbase.springbootrealworld.domain.article.entity;

import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

//import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class FavoriteDocument {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    protected String id;
    @Field
    private UserDocument author;
    @Field
    private ArticleDocument article;

}
