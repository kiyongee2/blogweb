package com.sudo.blogweb.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)   // 생성·수정 시각 자동 기록
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA가 쓰는 기본 생성자
@Getter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    // 긴 본문 (VARCHAR(255) 초과 방지)
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder  // 빌더 패턴 생성자
    public Article(String title, String content, String imageUrl){
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    //요청 받은 값으로 수정(트랜잭션 안에서 호출)
    public void update(String title, String content, String imageUrl){
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
