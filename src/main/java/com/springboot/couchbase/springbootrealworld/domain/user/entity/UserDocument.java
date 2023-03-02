package com.springboot.couchbase.springbootrealworld.domain.user.entity;



import com.springboot.couchbase.springbootrealworld.domain.common.entiity.BaseDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Getter
@Setter
@NoArgsConstructor
@Document
public class UserDocument extends BaseDocument {
    @Field
    private String username;
    @Field
    private String email;
    @Field
    private String password;
    @Field
    private String bio;
    @Field
    private String image;

    @Builder
    public UserDocument(String id, String username, String email, String password, String bio, String image) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }
}
