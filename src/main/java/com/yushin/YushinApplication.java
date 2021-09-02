package com.yushin;

import com.yushin.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * EnableJpaAuditing   Auditing 기능 활성화시키기위해 사용
 */
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
@SpringBootApplication
public class YushinApplication {

	public static void main(String[] args) {
		SpringApplication.run(YushinApplication.class, args);
	}

}
