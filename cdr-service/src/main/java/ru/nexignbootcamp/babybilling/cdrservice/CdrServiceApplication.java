package ru.nexignbootcamp.babybilling.cdrservice;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nexignbootcamp.babybilling.cdrservice.services.FileGenerator;
import ru.nexignbootcamp.babybilling.cdrservice.services.GeneratorService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@SpringBootApplication
@Log
public class CdrServiceApplication implements CommandLineRunner {

	@Autowired
	private GeneratorService generatorService;

	public static void main(String[] args) {
		SpringApplication.run(CdrServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Path cdrFilesDirectory = Paths.get("cdr-service/cdr_files");
		Path cdrFilesDirectory = Paths.get("/cdr_files");
		if (Files.exists(cdrFilesDirectory)) {
			Files.walk(cdrFilesDirectory)
					.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
		}
		Files.createDirectory(cdrFilesDirectory);
		generatorService.run();
	}
}
