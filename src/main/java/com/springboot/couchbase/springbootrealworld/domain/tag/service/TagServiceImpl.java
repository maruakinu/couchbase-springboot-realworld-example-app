package com.springboot.couchbase.springbootrealworld.domain.tag.service;

import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.ArticleDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.ArticleRepository;
import com.springboot.couchbase.springbootrealworld.domain.tag.entity.ArticleTagRelationDocument;
import com.springboot.couchbase.springbootrealworld.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

//    @Override
//    public List<String> listOfTags() {
//        return null;
//    //    return tagRepository.findAll().stream().map(ArticleTagRelationDocument::getTag).distinct().collect(Collectors.toList());
//    }

//    @Override
//    public List<String> getAllTags() {
//        return tagRepository.findAllTagList().stream().map(ArticleTagRelationDocument::getTagList).distinct().collect(Collectors.toList());
////        List<ArticleTagRelationDocument> articleEntities = tagRepository.findAllTags();
////        return articleEntities.stream().map(articleEntity -> convertEntityToDto(articleEntity)).collect(Collectors.toList());
//    }

    @Override
    public List<String> getAllTagList() {
        return tagRepository.findAllTagList().stream().map(ArticleTagRelationDocument::getTag).distinct().collect(Collectors.toList());
    }


}
