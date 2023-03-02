package com.springboot.couchbase.springbootrealworld.domain.article.repository;

import com.springboot.couchbase.springbootrealworld.domain.article.entity.ArticleDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.CommentDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.FavoriteDocument;
import org.springframework.data.couchbase.core.query.N1qlJoin;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("Couchbase")
@Collection("article")
public interface ArticleRepository extends CrudRepository<ArticleDocument, Long> {
    ArticleDocument findBySlug(String slug);

    List<ArticleDocument> findByAuthorId(String author);
    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter} ORDER BY createdAt DESC")
    ArticleDocument findBySlugs();
    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter}")
    List<ArticleDocument> findByAuthorId(List<String> id, Pageable pageable);

    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter} ORDER BY createdAt DESC")
    List<ArticleDocument> findAllArticles();

    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter} ORDER BY createdAt DESC")
    List<ArticleDocument> findAllArticlesYouFollow();

    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter}")
    List<ArticleDocument> findAllTags();


}
