package com.springboot.couchbase.springbootrealworld.domain.article.service;

import com.springboot.couchbase.springbootrealworld.domain.article.dto.CommentDto;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.FavoriteDto;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.ArticleDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.CommentDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.FavoriteDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.ArticleRepository;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.CommentRepository;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.FavoriteRepository;
import com.springboot.couchbase.springbootrealworld.domain.common.entiity.BaseDocument;
import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import com.springboot.couchbase.springbootrealworld.domain.profile.service.ProfileService;
import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import com.springboot.couchbase.springbootrealworld.domain.user.repository.UserRepository;
import com.springboot.couchbase.springbootrealworld.exception.AppException;
import com.springboot.couchbase.springbootrealworld.exception.Error;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ProfileService profileService;

    @Autowired
    private final UserRepository userRepository;


    @Override
    public List<FavoriteDto> getFavoritesBySlug(String slug, AuthUserDetails authUserDetails) {
        String articleId = articleRepository.findBySlug(slug).getId();
        List<FavoriteDocument> favoriteEntities = favoriteRepository.findByArticleId(articleId);
        return favoriteEntities.stream().map(favoriteEntity -> convertToDTO(authUserDetails, favoriteEntity)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(String slug, AuthUserDetails authUserDetails) {

        String articleId = articleRepository.findBySlug(slug).getId();
        List<FavoriteDocument> favoriteEntities = favoriteRepository.findByArticleId(articleId);
        favoriteRepository.deleteAll(favoriteEntities);

//        ArticleDocument found = articleRepository.findBySlug(slug);
//        System.out.println("This is Slug " + found);
//        favoriteRepository.deleteById(found.getId());
    }

    private FavoriteDto convertToDTO(AuthUserDetails authUserDetails, FavoriteDocument favoriteDocument) {
        ProfileDto author = profileService.getProfileByUserId(favoriteDocument.getAuthor().getId(), authUserDetails);
        return FavoriteDto.builder()
                .id(favoriteDocument.getId())
                .author(author)
                .build();
    }

//    @Override
//    public FavoriteDto getFavoriteByUserId(Long userId, AuthUserDetails authUserDetails) {
//        UserDocument user = userRepository.findById(userId).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
//        return convertToFavorite(user);
//    }
//
//    private FavoriteDto convertToFavorite(UserDocument user) {
//        return FavoriteDto.builder()
//                .id(String.valueOf(user.getId()))
//                .build();
//    }


}
