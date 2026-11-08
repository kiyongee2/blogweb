package com.sudo.blogweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login", "/signup", "/user").permitAll()  // 로그인 · 회원가입
                        .requestMatchers("/", "/articles", "/articles/*",
                                "/js/**", "/uploads/**", "/error").permitAll()       // 글 목록 · 상세보기 · 이미지 허용
                        .anyRequest().authenticated())    // 나머지는 로그인 필요
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true))
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .csrf(AbstractHttpConfigurer::disable)   // fetch로 POST/PUT/DELETE 하려면 필요
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
