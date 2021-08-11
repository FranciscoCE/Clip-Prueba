package com.example.clip1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.model.Registro;
import com.example.repo.IRegistroRepo;

@SpringBootApplication
@ComponentScan({"com.example.clip1"})
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repo")
public class Clip1Application {
	
	@Autowired
	IRegistroRepo iRegistroRepo;

	public static void main(String[] args) {
		SpringApplication.run(Clip1Application.class, args);
	}
	
	public void run(String... args) throws Exception {
		Registro s = new Registro();
		s.setHits(0);
		s.setKey("");
		iRegistroRepo.save(s);	
	}

}
