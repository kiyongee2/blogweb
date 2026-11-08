package com.sudo.blogweb.dto;

import com.sudo.blogweb.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    private String title;
    private String content;
    private String imageUrl;

    public ArticleResponse(Article article){
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageUrl = article.getImageUrl();
    }
}
