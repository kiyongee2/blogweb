package com.sudo.blogweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController  //문자열 반환
@Controller   // 문자열 대신 템플릿 이름을 반환 → templates/index.html
public class HomeController {

    @GetMapping("/")
    public String home(){
        // return "<h2>Blog Project!!</h2>";
        return "index";
    }
}
