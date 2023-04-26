package com.springboot.couchbase.springbootrealworld.domain.article.controller;

import com.springboot.couchbase.springbootrealworld.SpringBootRealworldApplication;
import com.springboot.couchbase.springbootrealworld.domain.article.dto.ArticleDto;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringBootRealworldApplication.class)
@ActiveProfiles("test")
@DisplayName("Author Resource REST API Tests")
@Tag("IntegrationTest")
public class ArticleControllerTest {

//    @Autowired
//    private TestRestTemplate restTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("when POST a new Author, then returns 201")
    public void givenNewAuthor_whenPostAuthor_thenReturns201() {
        //when(articleService.getArticle(eq(slug), any(AuthUserDetails.class))).then((Answer<ArticleDto>));
        //given
        HttpEntity<ArticleDto> request = new HttpEntity<>(
                ArticleDto.builder()
                        .title("mylife")
                        .description("iamtestingapp")
                        .body("itishard")
                        .tagList(List.of("tag1", "tag2"))
                        .build());

        //when
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/api/articles", request, Void.class);

        //then
        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().getLocation());
    }


}
