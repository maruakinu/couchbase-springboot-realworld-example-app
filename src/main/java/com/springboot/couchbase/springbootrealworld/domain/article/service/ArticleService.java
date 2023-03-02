package com.springboot.couchbase.springbootrealworld.domain.article.service;

import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.CommentDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.FavoriteDto;
import com.springboot.couchbase.springbootrealworld.domain.article.model.FeedParams;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;

import java.util.List;

public interface ArticleService {

//    ArticleDto createArticle(final ArticleDto article, final AuthUserDetails authUserDetails);

    ArticleDto createArticle(final ArticleDto article, final AuthUserDetails authUserDetails);

    ArticleDto getArticle(final String slug, final AuthUserDetails authUserDetails);
    ArticleDto getArticleFavorite(final String slug, final AuthUserDetails authUserDetails);

    ArticleDto updateArticle(final String slug, final ArticleDto.Update article, final AuthUserDetails authUserDetails);

    void deleteArticle(final String slug, final AuthUserDetails authUserDetails);

//    void deleteFavorite(final String commentId, final AuthUserDetails authUserDetails);

    ArticleDto favoriteArticle(final String slug, final AuthUserDetails authUserDetails);


//    void unfavoriteArticle(final String slug, final AuthUserDetails authUserDetails);
 //   void unfavoriteArticle(final String slug);

//    String unfavoriteArticle(final String slug);

  //  ArticleDto unfavoriteArticle(final String slug, final AuthUserDetails authUserDetails);

 //   List<ArticleDto> listArticle(final ArticleQueryParam articleQueryParam, final AuthUserDetails authUserDetails);

    List<ArticleDto> feedArticles(final AuthUserDetails authUserDetails, final FeedParams feedParams);

    List<ArticleDto> getAllArticles(final AuthUserDetails authUserDetails);

    List<ArticleDto> getAllArticlesYouFollow();

  //  ArticleDto getArticle(final String slug, final AuthUserDetails authUserDetails);




}
