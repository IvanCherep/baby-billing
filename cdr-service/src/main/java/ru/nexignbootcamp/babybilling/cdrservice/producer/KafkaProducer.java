package ru.nexignbootcamp.babybilling.cdrservice.producer;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topicName;

    public void sendMessage(String message, byte[] bytes) {
        Map<String, byte[]> map = new HashMap();
        map.put(message, bytes);

        ProducerRecord producerRecord = new ProducerRecord<String, Map>(topicName, message, map);
        kafkaTemplate.send(producerRecord);

    }

}
