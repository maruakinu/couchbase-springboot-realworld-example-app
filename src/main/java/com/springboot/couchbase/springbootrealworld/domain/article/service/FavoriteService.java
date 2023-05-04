package com.springboot.couchbase.springbootrealworld.domain.article.service;


import com.springboot.couchbase.springbootrealworld.domain.article.dto.FavoriteDto;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import java.util.List;

public interface FavoriteService {

    List<FavoriteDto> getFavoritesBySlug(final String slug, final AuthUserDetails authUserDetails);

    void delete(final String slug,  final AuthUserDetails authUserDetails);


}
