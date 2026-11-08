package com.sudo.blogweb.controller;

import com.sudo.blogweb.domain.Article;
import com.sudo.blogweb.dto.ArticleListViewResponse;
import com.sudo.blogweb.dto.ArticleViewResponse;
import com.sudo.blogweb.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    // 글 목록 화면
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles =
                blogService.findAll().stream()
                        .map(ArticleListViewResponse::new).toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    // 글 상세보기 화면
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        model.addAttribute("article",
                new ArticleViewResponse(blogService.findById(id)));
        return "article";
    }

    // id가 없으면 새 글, 있으면 수정 화면
    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model){
        if(id == null){
            model.addAttribute("article", new ArticleViewResponse());
        }else{
            model.addAttribute("article",
                    new ArticleViewResponse(blogService.findById(id)));
        }
        return "newArticle";
    }
}
