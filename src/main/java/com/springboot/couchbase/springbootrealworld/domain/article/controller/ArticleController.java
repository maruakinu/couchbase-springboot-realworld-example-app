package com.springboot.couchbase.springbootrealworld.domain.article.controller;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;

import com.couchbase.client.java.kv.GetResult;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.CommentDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.FavoriteDto;
import com.springboot.couchbase.springbootrealworld.domain.article.model.ArticleQueryParam;
import com.springboot.couchbase.springbootrealworld.domain.article.model.FeedParams;
import com.springboot.couchbase.springbootrealworld.domain.article.service.ArticleService;
import com.springboot.couchbase.springbootrealworld.domain.article.service.CommentService;
import com.springboot.couchbase.springbootrealworld.domain.article.service.FavoriteService;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private FavoriteService favoriteService;



    //Adding an Article
    @PostMapping
    public ArticleDto.SingleArticle<ArticleDto> createArticle(@RequestBody ArticleDto.SingleArticle<ArticleDto> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.createArticle(article.getArticle(), authUserDetails));
    }

    //Get an Article using slug
    @GetMapping("/{slug}")
    public ArticleDto.SingleArticle<ArticleDto> getArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.getArticle(slug, authUserDetails));
    }

    //Update an Article using slug
    @PutMapping("/{slug}")
    public ArticleDto.SingleArticle<ArticleDto> createArticle(@PathVariable String slug, @RequestBody ArticleDto.SingleArticle<ArticleDto.Update> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.updateArticle(slug, article.getArticle(), authUserDetails));
    }

    //Delete an Article using slug
    @DeleteMapping("/{slug}")
    public void deleteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        articleService.deleteArticle(slug, authUserDetails);
    }

    //Post a comment with a slug as parameter
    @PostMapping("/{slug}/comments")
    public CommentDto.SingleComment addCommentsToAnArticle(@PathVariable String slug, @RequestBody CommentDto.SingleComment comment, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.SingleComment.builder()
                .comment(commentService.addCommentsToAnArticle(slug, comment.getComment(), authUserDetails))
                .build();
    }

    //Get all comments to an article with a slug as parameter
    @GetMapping("/{slug}/comments")
    public CommentDto.MultipleComments getCommentsFromAnArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.MultipleComments.builder()
                .comments(commentService.getCommentsBySlug(slug, authUserDetails))
                .build();
    }

    //delete a comment to an article with a comment id as parameter
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable("commentId") String commentId, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        commentService.delete(commentId, authUserDetails);
    }

    //Post a favorite article with a slug as a parameter
    @PostMapping("/{slug}/favorite")
    public ArticleDto.SingleArticle<ArticleDto> favoriteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.favoriteArticle(slug, authUserDetails));
    }

    //Get a favorite article with a slug as a parameter
    @GetMapping("/{slug}/favorites")
    public FavoriteDto.MultipleFavorites getFavoritesFromAnArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return FavoriteDto.MultipleFavorites.builder()
                .favorites(favoriteService.getFavoritesBySlug(slug, authUserDetails))
                .build();
    }

    //Delete a favorite article with a slug as a parameter
    @DeleteMapping("/{slug}/favorite")
    public void deleteFavorite(@PathVariable("slug") String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        favoriteService.delete(slug, authUserDetails);
    }

    //Get all article with pagination and user authentication
    @GetMapping("/all")
    public ArticleDto.MultipleArticle feedArticles(@ModelAttribute @Valid FeedParams feedParams, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return ArticleDto.MultipleArticle.builder().articles(articleService.feedArticles(authUserDetails, feedParams)).build();
    }

    //Get all article with user authentication
    @GetMapping("/feed")
    public ArticleDto.MultipleArticle getArticles(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return ArticleDto.MultipleArticle.builder()
                .articles(articleService.getAllArticles(authUserDetails))
                .build();
    }

    //Get all article without user authentication
    @GetMapping
    public ArticleDto.MultipleArticle getArticlesYouFollow() {
        return ArticleDto.MultipleArticle.builder()
                .articles(articleService.getAllArticlesYouFollow())
                .build();
    }

}
