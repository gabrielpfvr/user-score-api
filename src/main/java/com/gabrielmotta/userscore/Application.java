package com.gabrielmotta.userscore;

import com.gabrielmotta.userscore.infra.properties.ClientProperties;
import com.gabrielmotta.userscore.infra.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ClientProperties.class, JwtProperties.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
