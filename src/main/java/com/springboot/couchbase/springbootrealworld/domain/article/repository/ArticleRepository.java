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
    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter} ORDER BY createdAt DESC")
    ArticleDocument findBySlugs();
    @Query("#{#n1ql.selectEntity} WHERE author.id IS NOT NULL AND #{#n1ql.filter}")
    List<ArticleDocument> findByAuthorId(List<Long> id, Pageable pageable);

    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter} ORDER BY createdAt DESC")
    List<ArticleDocument> findAllArticles();

  //  @Query("#{#n1ql.selectEntity} WHERE author.fllw = true AND #{#n1ql.filter}")
   // @Query("#{#n1ql.selectEntity} SELECT a FROM ArticleDocument a LEFT JOIN FollowDocument f ON f.followee.id = a.author.id WHERE a.title IS NOT NULL AND #{#n1ql.filter} ORDER BY a.createdAt DESC")
    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter} ORDER BY createdAt DESC")
    List<ArticleDocument> findAllArticlesYouFollow();

    @Query("#{#n1ql.selectEntity} WHERE title IS NOT NULL AND #{#n1ql.filter}")
    List<ArticleDocument> findAllTags();


//    @Query("SELECT a FROM ArticleDocument a LEFT JOIN FavoriteDocument f ON f.article.id = a.id ORDER BY a.createdAt DESC")
//    List<ArticleDocument> findListByPaging(Pageable pageable);
//    @Query("SELECT a FROM ArticleDocument a LEFT JOIN FavoriteDocument f ON f.article.id = a.id WHERE a.author.username = :username ORDER BY a.createdAt DESC")
//    List<ArticleDocument> findByAuthorName(String username, Pageable pageable);
//    @Query("SELECT a FROM ArticleDocument a JOIN ArticleTagRelationEntity t ON t.article.id = a.id LEFT JOIN FavoriteDocument f ON f.article.id = a.id WHERE t.tag = :tag ORDER BY a.createdAt DESC")
//    List<ArticleDocument> findByTag( String tag, Pageable pageable);
//    @Query("SELECT a FROM ArticleDocument a LEFT JOIN FavoriteDocument f ON f.article.id = a.id WHERE f.user.username = :username ORDER BY a.createdAt DESC")
//    List<ArticleDocument> findByFavoritedUsername(String username, Pageable pageable);

}
