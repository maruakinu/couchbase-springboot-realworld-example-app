package com.springboot.couchbase.springbootrealworld.domain.tag.controller;

import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.tag.dto.TagDto;
import com.springboot.couchbase.springbootrealworld.domain.tag.service.TagService;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.couchbase.springbootrealworld.domain.article.service.ArticleService;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    @Autowired
    private TagService tagService;


    @GetMapping
    public TagDto.TagList getArticleTags() {
        return TagDto.TagList.builder()
                .tags(tagService.getAllTagList())
                .build();
    }
}
