package ru.nexignbootcamp.babybilling.cdrservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.cdrservice.producer.KafkaProducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Преобразует файл в массив байтов и отправлят его в Kafka продюсер.
 */
@Service
@Slf4j
public class FileToKafkaProducerTransfer {

    @Autowired
    KafkaProducer kafkaProducer;

    public void sendPathToKafkaProducer(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            kafkaProducer.sendMessage(path.getFileName().toString(), bytes);
        } catch (IOException e) {
            log.error("Failed to convert file to byte array!");
            throw new RuntimeException(e);
        }
    }

}
