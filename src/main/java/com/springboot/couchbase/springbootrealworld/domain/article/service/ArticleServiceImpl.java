package com.springboot.couchbase.springbootrealworld.domain.article.service;

import com.springboot.couchbase.springbootrealworld.configuration.DBProperties;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.FavoriteDto;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.ArticleDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.FavoriteDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.model.FeedParams;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.ArticleRepository;
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
                .id(article.getId())
                .slug(slug)
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .taglist(article.getTagList())
                .author(author)
                .build();

                        for (String tag: article.getTagList()) {
                            tagRepository.save(ArticleTagRelationDocument.builder()
                                        .id(article.getId())
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
        Boolean favorited  = favoriteRepository.findByArticleIdAndAuthorId(result.getId(), authUserDetails.getId()).isPresent();
        int favoriteCount = (int) favoriteEntities.stream().count();

  //      return favoriteEntities.stream().map(favoriteEntity -> convertToDTO(authUserDetails, favoriteEntity)).collect(Collectors.toList());
        return convertEntityToDto(result, favorited, (long) favoriteCount, authUserDetails);
    }


    private ArticleDto convertEntityToDto(ArticleDocument entity, Boolean favorited, Long favoritesCount, AuthUserDetails authUserDetails) {
        ProfileDto author = profileService.getProfileByUserId(entity.getAuthor().getId(), authUserDetails);
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
        articleRepository.deleteById(found.getId());
    }

    @Transactional
    @Override
    public ArticleDto favoriteArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleDocument found = articleRepository.findBySlug(slug);

        favoriteRepository.findByArticleIdAndAuthorId(found.getId(), authUserDetails.getId())
                .ifPresent(favoriteEntity -> { throw new AppException(Error.ALREADY_FAVORITED_ARTICLE);});

        FavoriteDocument favorite = FavoriteDocument.builder()
                .article(found)
                .author(UserDocument.builder()
                        .id(authUserDetails.getId())
                        .bio(authUserDetails.getBio())
                        .email(authUserDetails.getEmail())
                        .build())
                .build();
        favoriteRepository.save(favorite);

        return getArticle(slug, authUserDetails);
    }


//    private ArticleDto convertEntityToDtos(ArticleDocument entity, Boolean favorited, Long favoritesCount, AuthUserDetails authUserDetails) {
//        ProfileDto author = profileService.getProfileByUserId(entity.getAuthor().getId(), authUserDetails);
//        FavoriteDto favoritedBy = favoriteService.getFavoriteByUserId(entity.getAuthor().getId(), authUserDetails);
//        return ArticleDto.builder()
//                .id(entity.getId())
//                .slug(entity.getSlug())
//                .title(entity.getTitle())
//                .description(entity.getDescription())
//                .body(entity.getBody())
//                .author(author)
//                .createdAt(entity.getCreatedAt())
//                .updatedAt(entity.getUpdatedAt())
//                .favoritedBy(favoritedBy.getAuthor())
//                .favorited(favorited)
//                .favoritesCount(favoritesCount)
//                .tagList(entity.getTagList())
//                .build();
//
//    }

    @Transactional
    @Override
    public ArticleDto unfavoriteArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleDocument found = articleRepository.findBySlug(slug);
        FavoriteDocument favorite = found.getFavoriteList().stream()
                .filter(favoriteEntity -> favoriteEntity.getArticle().getId().equals(found.getId())
                        && favoriteEntity.getAuthor().getId().equals(authUserDetails.getId())).findAny()
                .orElseThrow(() -> new AppException(Error.FAVORITE_NOT_FOUND));
        found.getFavoriteList().remove(favorite); // cascade REMOVE
        return getArticle(slug, authUserDetails);

    }

//    @Override
//    public String unfavoriteArticle(String slug ) {
//        ArticleDocument found = articleRepository.findBySlug(slug);
//        Cluster realworldCluster = Cluster.connect("couchbase://127.0.0.1", "Administrator", "Maru@Akinu11");
//        Bucket bucket = realworldCluster.bucket("sample");
//        N1QLQuery result = bucket.viewQuery(N1QLQuery.query("SELECT * FROM article"));
////        Collection favoriteCol = bucket.collection("article");
////        GetResult result = favoriteCol.get("1");
//        System.out.println(result);
//        return result.toString();
//    }



//    @Transactional
//    @Override
//    public void unfavoriteArticle(String slug) {
//        ArticleDocument found = articleRepository.findBySlug(slug);
//        Cluster realworldCluster = Cluster.connect("couchbase://127.0.0.1", "Administrator", "Maru@Akinu11");
//        Bucket bucket = realworldCluster.bucket("sample");;
//        Collection favoriteCol = bucket.collection("favorite");
//        GetResult result = favoriteCol.get("1");
//        System.out.println(result);
//        System.out.println(result);
//        String slug = result.contentAsObject().getString("slug");
//        String title = result.contentAsObject().getString("title");
//        String description = result.contentAsObject().getString("description");
//        String body = result.contentAsObject().getString("body");
//        System.out.println(title);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("title", title);
//        map.put("description", description);
//        map.put("body", body);

//        FavoriteDocument favorite = found.getFavoriteList().stream()
//                .filter(favoriteDocument -> favoriteDocument.getArticle().getId().equals(found.getId())
//                        && favoriteDocument.getUser().getId().equals(authUserDetails.getId())).findAny()
//                .orElseThrow(() -> new AppException(Error.FAVORITE_NOT_FOUND));
//        found.getFavoriteList().remove(favorite); // cascade REMOVE
//        return getArticle(slug, authUserDetails);
 //   }

//    @Transactional
//    @Override
//    public void deleteFavorite(String commentId, AuthUserDetails authUserDetails) {
//
//        FavoriteDocument favoriteEntity = favoriteRepository.findById(commentId);
//
//        favoriteRepository.delete(favoriteEntity);
//    }



//    @Transactional(readOnly = true)
//    @Override
//    public List<ArticleDto> listArticle(ArticleQueryParam articleQueryParam, AuthUserDetails authUserDetails) {
//        Pageable pageable = null;
//        if (articleQueryParam.getOffset() != null) {
//            pageable = PageRequest.of(articleQueryParam.getOffset(), articleQueryParam.getLimit());
//        }
//
//        List<ArticleDocument> articleEntities;
//        if (articleQueryParam.getTag() != null) {
//            articleEntities = articleRepository.findByTag(articleQueryParam.getTag(), pageable);
//        } else if  (articleQueryParam.getAuthor() != null) {
//            articleEntities = articleRepository.findByAuthorName(articleQueryParam.getAuthor(), pageable);
//        } else if (articleQueryParam.getFavorited() != null) {
//            articleEntities = articleRepository.findByFavoritedUsername(articleQueryParam.getFavorited(), pageable);
//        } else {
//            articleEntities = articleRepository.findListByPaging(pageable);
//        }
//
//        return convertToArticleList(articleEntities, authUserDetails);
//    }

    private List<ArticleDto> convertToArticleList(List<ArticleDocument> articleEntities, AuthUserDetails authUserDetails) {
        return articleEntities.stream().map(entity -> {
            List<FavoriteDocument> favorites = entity.getFavoriteList();
            Boolean favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getAuthor().getId().equals(authUserDetails.getId()));
            int favoriteCount = favorites.size();
            return convertEntityToDto(entity, favorited, (long) favoriteCount, authUserDetails);
        }).collect(Collectors.toList());
    }


//    @Transactional(readOnly = true)
//    @Override
//    public List<ArticleDto> listArticle(ArticleQueryParam articleQueryParam) {
//        Pageable pageable = null;
//        if (articleQueryParam.getOffset() != null) {
//            pageable = PageRequest.of(articleQueryParam.getOffset(), articleQueryParam.getLimit());
//        }
//
//        List<ArticleDocument> articleEntities;
//        if (articleQueryParam.getTag() != null) {
//            articleEntities = articleRepository.findByTag(articleQueryParam.getTag(), pageable);
//        }
//        else if  (articleQueryParam.getAuthor() != null) {
//            articleEntities = articleRepository.findByAuthorName(articleQueryParam.getAuthor(), pageable);
//        }
//        else if (articleQueryParam.getFavorited() != null) {
//            articleEntities = articleRepository.findByFavoritedUsername(articleQueryParam.getFavorited(), pageable);
//        }
//        else {
//            articleEntities = articleRepository.findListByPaging(pageable);
//        }
//        articleEntities = articleRepository.findListByPaging(pageable);
//
//        return convertToArticleList(articleEntities);
//    }

//    private List<ArticleDto> convertToArticleList(List<ArticleDocument> articleEntities) {
//        return articleEntities.stream().map(entity -> {
//            List<FavoriteDocument> favorites = entity.getFavoriteList();
// //           Boolean favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(authUserDetails.getId()));
//            int favoriteCount = favorites.size();
//            return convertEntityToDto(entity, false, (long) favoriteCount, false);
//        }).collect(Collectors.toList());
//    }


    @Override
    public List<ArticleDto> feedArticles(AuthUserDetails authUserDetails, FeedParams feedParams) {
        List<Long> feedAuthorIds = followRepository.findByFollowerId(authUserDetails.getId()).stream().map(FollowDocument::getFollowee).map(UserDocument::getId).collect(Collectors.toList());
        return articleRepository.findByAuthorId(feedAuthorIds, PageRequest.of(feedParams.getOffset(), feedParams.getLimit())).stream().map(entity -> {

//            List<FavoriteDocument> favorites = entity.getFavoriteList();
//            Boolean favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getAuthor().getId().equals(authUserDetails.getId()));
//            int favoriteCount = favorites.size();
//            int favoriteCount = 1;
//            Boolean favorited = true;

            List<FavoriteDocument> favoriteEntities = favoriteRepository.findAllFavorites();
            Boolean favoriteds  = favoriteRepository.findByAuthorId(authUserDetails.getId()).isPresent();
            int favoriteCounts = (int) favoriteEntities.stream().count();

            return convertEntityToDto(entity, favoriteds, (long) favoriteCounts, authUserDetails);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getAllArticles(AuthUserDetails authUserDetails) {
        List<ArticleDocument> articleEntities = articleRepository.findAllArticles();
        List<FavoriteDocument> favoriteEntities = favoriteRepository.findAllFavorites();
        Boolean favoriteds  = favoriteRepository.findByAuthorId(authUserDetails.getId()).isPresent();
        int favoriteCounts = (int) favoriteEntities.stream().count();

//        int favoriteCounts = 1;
//        Boolean favoriteds = true;

        return articleEntities.stream().map(articleEntity -> convertEntityToDto(articleEntity, favoriteds, (long) favoriteCounts, authUserDetails)).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getAllArticlesYouFollow(AuthUserDetails authUserDetails) {
        List<ArticleDocument> articleEntities = articleRepository.findAllArticlesYouFollow();
        int favoriteCount = 1;
        Boolean favorited = true;
        return articleEntities.stream().map(articleEntity -> convertEntityToDto(articleEntity, favorited, (long) favoriteCount, authUserDetails)).collect(Collectors.toList());
    }




}
