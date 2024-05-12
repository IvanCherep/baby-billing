package ru.nexignbootcamp.babybilling.brtservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import ru.nexignbootcamp.babybilling.brtservice.services.CDRFileHandlerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
public class KafkaConsumer {

    @Autowired
    CDRFileHandlerService cdrFileHandlerService;

    @KafkaListener(id = "consumer", topics = {"${kafka.topic}"})
    public void onMessage(ConsumerRecord<String, Map> record) {
        Path cdrFile = Paths.get(record.key());
        byte[] cdrFileInBytes = (byte[]) record.value().get(record.key());
        try {
            Files.write(cdrFile, cdrFileInBytes);
            log.info(String.format("File %s received", record.key()));
            cdrFileHandlerService.authorize(cdrFile);
        } catch (IOException e) {
            log.error("Failed to write bytes into file.");
            throw new RuntimeException(e);
        }

    }

}
