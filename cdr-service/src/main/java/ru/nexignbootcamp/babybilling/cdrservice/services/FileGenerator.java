package ru.nexignbootcamp.babybilling.cdrservice.services;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.CDREntity;
import ru.nexignbootcamp.babybilling.cdrservice.mappers.impl.CDREntityStringMapperWithoutIdImpl;

import java.io.FileWriter;
import java.io.IOException;

@Service
@Log
public class FileGenerator {

    GeneratorService generatorService;

    CDREntityStringMapperWithoutIdImpl cdrEntityStringMapperWithoutId;

    public FileGenerator(GeneratorService generatorService, CDREntityStringMapperWithoutIdImpl cdrEntityStringMapperWithoutId) {
        this.generatorService = generatorService;
        this.cdrEntityStringMapperWithoutId = cdrEntityStringMapperWithoutId;
    }

    public void makeFile() {
        int fileNumber = 1;
        int cdrCounter = 0;
        try (FileWriter fileWriter = new FileWriter("cdr-service/cdr_files/cdr_file_" + fileNumber + ".txt")){
            while (cdrCounter < 5) {
                CDREntity[] cdrToAndCdrFrom = generatorService.generateCdr();
                CDREntity cdrTo = cdrToAndCdrFrom[0];
                CDREntity cdrFrom = cdrToAndCdrFrom[1];
                cdrCounter += 1;
                fileWriter.write(cdrEntityStringMapperWithoutId.mapTo(cdrTo));
                fileWriter.write(cdrEntityStringMapperWithoutId.mapTo(cdrFrom));
            }
        } catch (IOException e) {
            log.warning("FileWriter exception");
        }
    }
}
