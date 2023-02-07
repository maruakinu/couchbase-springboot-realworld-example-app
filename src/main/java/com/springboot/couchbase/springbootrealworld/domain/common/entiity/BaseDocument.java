package com.springboot.couchbase.springbootrealworld.domain.common.entiity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.Field;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseDocument {
    @Id
    protected Long id;

    @Field
    protected LocalDateTime createdAt = LocalDateTime.now();


    @Field
    protected LocalDateTime updatedAt = LocalDateTime.now();
}
