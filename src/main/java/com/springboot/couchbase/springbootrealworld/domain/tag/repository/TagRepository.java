package com.springboot.couchbase.springbootrealworld.domain.tag.repository;

import com.springboot.couchbase.springbootrealworld.domain.article.entity.ArticleDocument;
import com.springboot.couchbase.springbootrealworld.domain.tag.entity.ArticleTagRelationDocument;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("Couchbase")
@Collection("tags")
public interface TagRepository extends CrudRepository<ArticleTagRelationDocument, Long> {

  @Query("#{#n1ql.selectEntity} WHERE tag IS NOT NULL AND #{#n1ql.filter}")
  List<ArticleTagRelationDocument> findAllTagList();
}
