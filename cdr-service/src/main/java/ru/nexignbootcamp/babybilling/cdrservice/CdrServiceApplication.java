package ru.nexignbootcamp.babybilling.cdrservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nexignbootcamp.babybilling.cdrservice.services.FileGenerator;

@SpringBootApplication
public class CdrServiceApplication implements CommandLineRunner {

	@Autowired
	private FileGenerator fileGenerator;

	public static void main(String[] args) {
		SpringApplication.run(CdrServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileGenerator.makeFile();
	}
}
