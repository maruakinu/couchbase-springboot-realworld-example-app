package com.springboot.couchbase.springbootrealworld.domain.article.repository;


import com.springboot.couchbase.springbootrealworld.domain.article.entity.CommentDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.FavoriteDocument;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Scope("Couchbase")
@Collection("comment")
public interface CommentRepository extends CrudRepository<CommentDocument, Long> {
    List<CommentDocument> findByArticleIdOrderByCreatedAtDesc(String articleId);

    List<CommentDocument> findById(String id);
}

