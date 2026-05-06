package com.afrobad.VeinLinker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VeinLinkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeinLinkerApplication.class, args);
	}

}
