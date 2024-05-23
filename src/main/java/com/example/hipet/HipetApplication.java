package com.example.hipet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HipetApplication {

	public static void main(String[] args) {
		SpringApplication.run(HipetApplication.class, args);
	}

}
