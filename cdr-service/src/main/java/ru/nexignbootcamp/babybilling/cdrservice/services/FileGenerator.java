package ru.nexignbootcamp.babybilling.cdrservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.CDREntity;
import ru.nexignbootcamp.babybilling.cdrservice.mappers.impl.CDREntityStringMapperImpl;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.CDRRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@Slf4j
public class FileGenerator {

    static private int fileNumber = 1;

    static private int cdrCounter = 0;

    @Autowired
    private CDRRepository cdrRepository;

    @Autowired
    CDREntityStringMapperImpl cdrEntityStringMapperWithoutId;

    @Autowired
    FileToKafkaProducerTransfer fileToKafkaProducerTransfer;


    public void writeCdrToFile(CDREntity[] cdrPair) {
//        Path cdrFile = Paths.get("cdr-service/cdr_files/cdr_file_" + fileNumber + ".txt");
        Path cdrFile = Paths.get("/cdr_files/cdr_file_" + fileNumber + ".txt");

        try {
            if (!Files.exists(cdrFile)) {
                Files.createFile(cdrFile);
                log.info(cdrFile.toString() + " is created.");
            }
            Files.writeString(cdrFile, cdrEntityStringMapperWithoutId.mapTo(cdrPair[0]), StandardOpenOption.APPEND);
            Files.writeString(cdrFile, cdrEntityStringMapperWithoutId.mapTo(cdrPair[1]), StandardOpenOption.APPEND);
            cdrRepository.save(cdrPair[0]);
            cdrRepository.save(cdrPair[1]);
        } catch (IOException e) {
            log.warn("IOException");
        }
        cdrCounter += 2;
        if (cdrCounter >= 10) {
            fileToKafkaProducerTransfer.sendPathToKafkaProducer(cdrFile);
            log.info(cdrFile.toString() + " sent to the BRT service.");
            cdrCounter = 0;
            fileNumber += 1;
        }
    }

}
