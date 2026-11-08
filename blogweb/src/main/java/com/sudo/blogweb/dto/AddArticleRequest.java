package com.sudo.blogweb.dto;

import com.sudo.blogweb.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;
    private String imageUrl;

    // DTO를 엔티티로 만들어 주는 메서드
    public Article toEntity(){
        return Article.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}
