package com.sudo.blogweb.service;

import com.sudo.blogweb.domain.Article;
import com.sudo.blogweb.dto.AddArticleRequest;
import com.sudo.blogweb.dto.UpdateArticleRequest;
import com.sudo.blogweb.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    // 전체 글 조회
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    // 글 추가
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    // 글 조회
    public Article findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
    }

    // 글 삭제
    public void delete(Long id){
        blogRepository.deleteById(id);
    }

    // 글 수정
    @Transactional
    public Article update(Long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
        article.update(request.getTitle(), request.getContent(), request.getImageUrl());
        return article;
    }


}
