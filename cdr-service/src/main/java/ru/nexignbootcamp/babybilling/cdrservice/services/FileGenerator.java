package ru.nexignbootcamp.babybilling.cdrservice.services;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.CDREntity;
import ru.nexignbootcamp.babybilling.cdrservice.mappers.impl.CDREntityStringMapperWithoutIdImpl;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.CDRRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Log
public class FileGenerator {

    private CDRRepository cdrRepository;
    static private int fileNumber = 1;
    static private int cdrCounter = 0;

    CDREntityStringMapperWithoutIdImpl cdrEntityStringMapperWithoutId;

    public FileGenerator(CDRRepository cdrRepository, CDREntityStringMapperWithoutIdImpl cdrEntityStringMapperWithoutId) {
        this.cdrRepository = cdrRepository;
        this.cdrEntityStringMapperWithoutId = cdrEntityStringMapperWithoutId;
    }

    public void writeCdrToFile(CDREntity[] cdrPair) {
//        Path cdrFile = Paths.get("cdr-service/cdr_files/cdr_file_" + fileNumber + ".txt");
        Path cdrFile = Paths.get("/cdr_files/cdr_file_" + fileNumber + ".txt");

        try {
            if (!Files.exists(cdrFile)) {
                Files.createFile(cdrFile);
                log.info(cdrFile.toString() + " is created.");
            }
            Files.writeString(cdrFile, cdrEntityStringMapperWithoutId.mapTo(cdrPair[0]));
            Files.writeString(cdrFile, cdrEntityStringMapperWithoutId.mapTo(cdrPair[1]));
            cdrRepository.save(cdrPair[0]);
            cdrRepository.save(cdrPair[1]);
        } catch (IOException e) {
            log.warning("IOException");
        }
        cdrCounter += 2;
        if (cdrCounter >= 10) {
//            send(cdrFile)
            cdrCounter = 0;
            log.info(cdrFile.toString() + " sent to the BRT service.");
            fileNumber += 1;
        }
    }
}
