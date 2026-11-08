package com.sudo.blogweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing   // @CreatedDate, @LastModifiedDate 자동 기록
@SpringBootApplication
public class BlogwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogwebApplication.class, args);
	}

}
