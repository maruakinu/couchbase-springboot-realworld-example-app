package com.springboot.couchbase.springbootrealworld.domain.article.repository;

import com.springboot.couchbase.springbootrealworld.domain.article.dto.FavoriteDto;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.FavoriteDocument;
import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import com.springboot.couchbase.springbootrealworld.domain.profile.entity.FollowDocument;
import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Scope("Couchbase")
@Collection("favorite")
public interface FavoriteRepository extends CrudRepository<FavoriteDocument, Long> {
    @Query("#{#n1ql.selectEntity} WHERE id IS NOT NULL AND #{#n1ql.filter}")
    List<FavoriteDocument> findBySlug(String slug);
    Optional<FavoriteDocument> findByArticleIdAndAuthorEmail(String articleId, String author);


    Optional<FavoriteDocument> findByAuthorEmail(String author);

    List<FavoriteDocument> findByArticleId(String articleId);

    @Query("#{#n1ql.selectEntity} WHERE author IS NOT NULL AND #{#n1ql.filter}")
    List<FavoriteDocument> findAllFavorites();



}
