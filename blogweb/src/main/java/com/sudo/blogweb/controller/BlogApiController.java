package com.sudo.blogweb.controller;

import com.sudo.blogweb.domain.Article;
import com.sudo.blogweb.dto.AddArticleRequest;
import com.sudo.blogweb.dto.ArticleResponse;
import com.sudo.blogweb.dto.UpdateArticleRequest;
import com.sudo.blogweb.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;

    // 글 목록
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }

    // 글 추가
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    // 단건 조회
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id){
        return ResponseEntity.ok().body(new ArticleResponse(blogService.findById(id)));
    }

    // 글 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id){
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 글 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id,
                     @RequestBody UpdateArticleRequest request){
        return ResponseEntity.ok().body(blogService.update(id, request));
    }
}
