package com.yushin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * EnableJpaAuditing   Auditing 기능 활성화시키기위해 사용
 */
@EnableJpaAuditing
@SpringBootApplication
public class YushinApplication {

	public static void main(String[] args) {
		SpringApplication.run(YushinApplication.class, args);
	}

}
