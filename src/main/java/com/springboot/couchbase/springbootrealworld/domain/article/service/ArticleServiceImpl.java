package com.springboot.couchbase.springbootrealworld.domain.article.service;

import com.springboot.couchbase.springbootrealworld.configuration.DBProperties;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.CommentDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.FavoriteDto;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.ArticleDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.CommentDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.FavoriteDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.model.FeedParams;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.ArticleRepository;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.CommentRepository;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.FavoriteRepository;
import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import com.springboot.couchbase.springbootrealworld.domain.profile.entity.FollowDocument;
import com.springboot.couchbase.springbootrealworld.domain.profile.repository.FollowRepository;
import com.springboot.couchbase.springbootrealworld.domain.profile.service.ProfileService;
import com.springboot.couchbase.springbootrealworld.domain.tag.entity.ArticleTagRelationDocument;
import com.springboot.couchbase.springbootrealworld.domain.tag.repository.TagRepository;
import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import com.springboot.couchbase.springbootrealworld.exception.AppException;
import com.springboot.couchbase.springbootrealworld.exception.Error;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ProfileService profileService;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DBProperties dbProperties;
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public ArticleDto createArticle(ArticleDto article, AuthUserDetails authUserDetails) {
        String slug = String.join("-", article.getTitle().split(" "));
        UserDocument author = UserDocument.builder()
                .id(authUserDetails.getId())
                .build();

        ArticleDocument articleDocument = ArticleDocument.builder()
                .slug(slug)
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .taglist(article.getTagList())
                .author(author)
                .build();

                        for (String tag: article.getTagList()) {
                            tagRepository.save(ArticleTagRelationDocument.builder()
           //                             .id(article.getId())
                                        .article(articleDocument)
                                        .tag(tag)
                                        .build());
                            }
//        List<ArticleTagRelationDocument> tagList = new ArrayList<>();
//        for (String tag: article.getTagList()) {
//            tagList.add(ArticleTagRelationDocument.builder()
//                    .article(articleDocument)
//                    .tag(tag)
//                    .build());
//        }
//        tagRepository.save(tagList);

        articleRepository.save(articleDocument);

        return convertEntityToDto(articleDocument, false, 0L, authUserDetails);


    }

    @Override
    public ArticleDto getArticleFavorite(String slug, AuthUserDetails authUserDetails) {
        ArticleDocument result = articleRepository.findBySlug(slug);
        return convertEntityToDto(result, false, 0L, authUserDetails);
    }

    @Override
    public ArticleDto getArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleDocument result = articleRepository.findBySlug(slug);
//        List<FavoriteDocument> favorites = result.getFavoriteList();
////        Boolean favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(authUserDetails.getId()));
//        long favoriteCount = 1;
//        Boolean favorited = true;

       // ArticleDocument result = articleRepository.findBySlug(slug);
        List<FavoriteDocument> favoriteEntities = favoriteRepository.findAllFavorites();

  //      List<FavoriteDocument> favorites = result.getFavoriteList();
   //     Boolean favorited = favoriteEntities.stream().anyMatch(favoriteEntity -> favoriteEntity.getAuthor().getId().equals(authUserDetails.getId()));
     //   int favoriteCount = (int) favoriteEntities.stream().count();

    //    ArticleDocument result  = articleRepository.findBySlug(slug);
        Boolean favorited  = favoriteRepository.findByArticleIdAndAuthorEmail(result.getId(), authUserDetails.getEmail()).isPresent();
        int favoriteCount = (int) favoriteEntities.stream().count();

  //      return favoriteEntities.stream().map(favoriteEntity -> convertToDTO(authUserDetails, favoriteEntity)).collect(Collectors.toList());
        return convertEntityToDto(result, favorited, (long) favoriteCount, authUserDetails);
    }


    private ArticleDto convertEntityToDto(ArticleDocument entity, Boolean favorited, Long favoritesCount, AuthUserDetails authUserDetails) {
        ProfileDto author = profileService.getProfileByUserIds(entity.getAuthor().getId());
        return ArticleDto.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .body(entity.getBody())
                .author(author)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .favorited(favorited)
                .favoritesCount(favoritesCount)
                .tagList(entity.getTagList().stream().collect(Collectors.toList()))
                //.tagList(entity.getTagList())
                .build();
    }

    @Transactional
    @Override
    public ArticleDto updateArticle(String slug, ArticleDto.Update article, AuthUserDetails authUserDetails) {
        ArticleDocument found = articleRepository.findBySlug(slug);

        if (article.getTitle() != null) {
            String newSlug = String.join("-", article.getTitle().split(" "));
            found.setTitle(article.getTitle());
            found.setSlug(newSlug);
        }

        if (article.getDescription() != null) {
            found.setDescription(article.getDescription());
        }

        if (article.getBody() != null) {
            found.setBody(article.getBody());
        }

        articleRepository.save(found);

        return getArticle(slug, authUserDetails);
    }

    @Transactional
    @Override
    public void deleteArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleDocument found = articleRepository.findBySlug(slug);
        System.out.println("This is Slug" + found.getId());
        articleRepository.delete(found);
    }

    @Transactional
    @Override
    public ArticleDto favoriteArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleDocument found = articleRepository.findBySlug(slug);

        favoriteRepository.findByArticleIdAndAuthorEmail(found.getId(), authUserDetails.getEmail())
                .ifPresent(favoriteEntity -> { throw new AppException(Error.ALREADY_FAVORITED_ARTICLE);});

        FavoriteDocument favorite = FavoriteDocument.builder()
                .article(found)
                .author(UserDocument.builder()
                        .id(String.valueOf(authUserDetails.getId()))
                        .bio(authUserDetails.getBio())
                        .email(authUserDetails.getEmail())
                        .build())
                .build();
        favoriteRepository.save(favorite);

        return getArticle(slug, authUserDetails);
    }



//    @Transactional
//    @Override
//    public ArticleDto unfavoriteArticle(String slug, AuthUserDetails authUserDetails) {
//        ArticleDocument found = articleRepository.findBySlug(slug);
//        FavoriteDocument favorite = found.getFavoriteList().stream()
//                .filter(favoriteEntity -> favoriteEntity.getArticle().getId().equals(found.getId())
//                        && favoriteEntity.getAuthor().getId().equals(authUserDetails.getId())).findAny()
//                .orElseThrow(() -> new AppException(Error.FAVORITE_NOT_FOUND));
//        found.getFavoriteList().remove(favorite); // cascade REMOVE
//        return getArticle(slug, authUserDetails);
//
//    }





    private List<ArticleDto> convertToArticleList(List<ArticleDocument> articleEntities, AuthUserDetails authUserDetails) {
        return articleEntities.stream().map(entity -> {
            List<FavoriteDocument> favorites = entity.getFavoriteList();
            Boolean favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getAuthor().getId().equals(authUserDetails.getId()));
            int favoriteCount = favorites.size();
            return convertEntityToDto(entity, favorited, (long) favoriteCount, authUserDetails);
        }).collect(Collectors.toList());
    }


    @Override
    public List<ArticleDto> feedArticles(AuthUserDetails authUserDetails, FeedParams feedParams) {
        List<String> feedAuthorIds = followRepository.findByFollowerId(authUserDetails.getId().toString()).stream().map(FollowDocument::getFollowee).map(UserDocument::getId).collect(Collectors.toList());
        return articleRepository.findByAuthorId(feedAuthorIds, PageRequest.of(feedParams.getOffset(), feedParams.getLimit())).stream().map(entity -> {

//            List<FavoriteDocument> favorites = entity.getFavoriteList();
//            Boolean favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getAuthor().getId().equals(authUserDetails.getId()));
//            int favoriteCount = favorites.size();
//            int favoriteCount = 1;
//            Boolean favorited = true;

            List<FavoriteDocument> favoriteEntities = favoriteRepository.findAllFavorites();
            Boolean favoriteds  = favoriteRepository.findByAuthorEmail(authUserDetails.getEmail()).isPresent();
            int favoriteCounts = (int) favoriteEntities.stream().count();

            return convertEntityToDto(entity, true, (long) favoriteCounts, authUserDetails);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getAllArticles(AuthUserDetails authUserDetails) {

        List<ArticleDocument> result = articleRepository.findByAuthorId(authUserDetails.getId());

        List<ArticleDocument> articleEntities = articleRepository.findAllArticles();
        List<FavoriteDocument> favoriteEntities = favoriteRepository.findAllFavorites();
        Boolean favorited  = favoriteRepository.findByAuthorEmail(authUserDetails.getEmail()).isPresent();
        int favoriteCount = (int) favoriteEntities.stream().count();

//        int favoriteCounts = 1;
//        Boolean favoriteds = true;

        return result.stream().map(articleEntity -> convertEntityToDto(articleEntity, favorited, (long) favoriteCount, authUserDetails)).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getAllArticlesYouFollow() {

   //     ArticleDocument  result = articleRepository.findAllArticlesYouFollows();
        List<ArticleDocument> articleEntities = articleRepository.findAllArticlesYouFollow();
//        int favoriteCount = 1;
//        Boolean favorited = true;
        List<FavoriteDocument> favoriteEntities = favoriteRepository.findAllFavorites();
//        Boolean favorited  = true;
//        int favoriteCount = (int) favoriteEntities.stream().count();
 //       Boolean favorited  = favoriteRepository.findByAuthorId(result.getId()).isPresent();
        int favoriteCount = (int) favoriteEntities.stream().count();
        return articleEntities.stream().map(articleEntity -> convertEntityToDtos(articleEntity, true, (long) favoriteCount)).collect(Collectors.toList());
    }

    private ArticleDto convertEntityToDtos(ArticleDocument entity, Boolean favorited, Long favoritesCount) {
        ProfileDto author = profileService.getProfileByUserIds(entity.getAuthor().getId());
        return ArticleDto.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .body(entity.getBody())
                .author(author)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .favorited(favorited)
                .favoritesCount(favoritesCount)
                .tagList(entity.getTagList())
                .build();
    }






}
