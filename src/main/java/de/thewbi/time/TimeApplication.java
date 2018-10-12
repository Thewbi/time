package de.thewbi.time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"de.thewbi.time"})
@EnableJpaRepositories
public class TimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeApplication.class, args);
	}
}
