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

    //private Cluster cluster;
    //private Collection articleCol;
    //private DBProperties dbProperties;
    //private Bucket bucket;

    //public ArticleController( Cluster cluster, Bucket bucket, DBProperties dbProperties) {
        //System.out.println("Initializing profile controller, cluster: " + cluster + "; bucket: " + bucket);
        //this.cluster = cluster;
        //this.bucket = bucket;
        //this.articleCol = bucket.collection(ARTICLE);
        //this.dbProperties = dbProperties;
    //}

//    @PostMapping
//    public ArticleDto.SingleArticle<ArticleDto> createArticle(@RequestBody ArticleDto.SingleArticle<ArticleDto> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        AuthUserDetails authDetails = new AuthUserDetails(2L, "marloaquino080621@gmail.com", null);
//        return new ArticleDto.SingleArticle<>(articleService.createArticle(article.getArticle(), authDetails));
//    }


    @PostMapping
    public ArticleDto.SingleArticle<ArticleDto> createArticle(@RequestBody ArticleDto.SingleArticle<ArticleDto> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        AuthUserDetails authDetails = new AuthUserDetails(2L, "marloaquino080621@gmail.com", null);
        return new ArticleDto.SingleArticle<>(articleService.createArticle(article.getArticle(), authUserDetails));
    }

    @GetMapping("/{slug}")
    public ArticleDto.SingleArticle<ArticleDto> getArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.getArticle(slug, authUserDetails));
    }

    @PutMapping("/{slug}")
    public ArticleDto.SingleArticle<ArticleDto> createArticle(@PathVariable String slug, @RequestBody ArticleDto.SingleArticle<ArticleDto.Update> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.updateArticle(slug, article.getArticle(), authUserDetails));
    }

    @DeleteMapping("/{slug}")
    public void deleteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        articleService.deleteArticle(slug, authUserDetails);
    }

//    @PostMapping("/{slug}/comments")
//    public CommentDto.SingleComment addCommentsToAnArticle(@PathVariable String slug, @RequestBody CommentDto.SingleComment comment, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        return CommentDto.SingleComment.builder()
//                .comment(commentService.addCommentsToAnArticle(slug, comment.getComment(), authUserDetails))
//                .build();
//    }

//    @PostMapping("/{slug}/comments")
//    public CommentDto.SingleComment addCommentsToAnArticle(@PathVariable String slug, @RequestBody CommentDto.@NotNull SingleComment comment, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        return CommentDto.SingleComment.builder()
//                .comment(commentService.addCommentsToAnArticle(slug, comment.getComment(), authUserDetails))
//                .build();
//    }

    @PostMapping("/{slug}/comments")
    public CommentDto.SingleComment addCommentsToAnArticle(@PathVariable String slug, @RequestBody CommentDto.SingleComment comment, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.SingleComment.builder()
                .comment(commentService.addCommentsToAnArticle(slug, comment.getComment(), authUserDetails))
                .build();
    }


    @GetMapping("/{slug}/comments")
    public CommentDto.MultipleComments getCommentsFromAnArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.MultipleComments.builder()
                .comments(commentService.getCommentsBySlug(slug, authUserDetails))
                .build();
    }
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable("commentId") String commentId, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        commentService.delete(commentId, authUserDetails);
    }

    @PostMapping("/{slug}/favorite")
    public ArticleDto.SingleArticle<ArticleDto> favoriteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.favoriteArticle(slug, authUserDetails));
    }

    @GetMapping("/{slug}/favorites")
    public FavoriteDto.MultipleFavorites getFavoritesFromAnArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return FavoriteDto.MultipleFavorites.builder()
                .favorites(favoriteService.getFavoritesBySlug(slug, authUserDetails))
                .build();
    }

    @DeleteMapping("/{slug}/favorite")
    public void deleteFavorite(@PathVariable("slug") String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        favoriteService.delete(slug, authUserDetails);
    }

//    @DeleteMapping("/{slug}/favorite")
//    public ArticleDto.SingleArticle<ArticleDto> unfavoriteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        return new ArticleDto.SingleArticle<>(articleService.unfavoriteArticle(slug, authUserDetails));
//    }

//    @DeleteMapping("/{slug}/favorite")
//    public void unfavoriteArticle(@PathVariable String slug) {
//        articleService.unfavoriteArticle(slug);
//    }

//    @GetMapping("/{slug}/favorite")
//    public String unfavoriteArticle(@PathVariable String slug) {
//        return articleService.unfavoriteArticle(slug);
//    }
//

//    @DeleteMapping("/favorite/{commentId}")
//    public void deleteFavorite( @PathVariable("commentId") String commentId, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        articleService.deleteFavorite(commentId, authUserDetails);
//    }


//    @GetMapping
//    public ArticleDto.MultipleArticle listArticles(@ModelAttribute ArticleQueryParam articleQueryParam) {
//        return ArticleDto.MultipleArticle.builder().articles(articleService.listArticle(articleQueryParam)).build();
//    }

//    @GetMapping
//    public ArticleDto.MultipleArticle listArticles(@ModelAttribute ArticleQueryParam articleQueryParam, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        return ArticleDto.MultipleArticle.builder().articles(articleService.listArticle(articleQueryParam, authUserDetails)).build();
//    }

    @GetMapping("/all")
    public ArticleDto.MultipleArticle feedArticles(@ModelAttribute @Valid FeedParams feedParams, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return ArticleDto.MultipleArticle.builder().articles(articleService.feedArticles(authUserDetails, feedParams)).build();
    }
    // Get All Articles

//    @GetMapping("/feed")
//    public ArticleDto.MultipleArticle getArticles(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
////       AuthUserDetails authDetails = new AuthUserDetails(2L, "marloaquino080621@gmail.com", null);
//        return ArticleDto.MultipleArticle.builder()
//                .articles(articleService.getAllArticles(authUserDetails))
//                .build();
//    }

    @GetMapping("/feed")
    public ArticleDto.MultipleArticle getArticles(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return ArticleDto.MultipleArticle.builder()
                .articles(articleService.getAllArticles(authUserDetails))
                .build();
    }


//    @GetMapping
//    public ArticleDto.MultipleArticle getArticlesYouFollow(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        return ArticleDto.MultipleArticle.builder()
//                .articles(articleService.getAllArticlesYouFollow(authUserDetails))
//                .build();
//    }

    @GetMapping
    public ArticleDto.MultipleArticle getArticlesYouFollow() {
        return ArticleDto.MultipleArticle.builder()
                .articles(articleService.getAllArticlesYouFollow())
                .build();
    }

//    @GetMapping("/follow")
//    public ArticleDto.MultipleArticle getArticlesYouFollow(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
//        return ArticleDto.MultipleArticle.builder()
//                .articles(articleService.getAllArticlesYouFollow(authUserDetails))
//                .build();
//    }



}
