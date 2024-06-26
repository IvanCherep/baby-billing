package ru.nexignbootcamp.babybilling.brtservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Преобразует массив байт в объект типа Map&ltString, byte[]&gt.
 * Используется в качестве VALUE_DESERIALIZER_CLASS_CONFIG для
 * конфигурации Apache Kafka.
 */
@Slf4j
public class MapDeserializer implements Deserializer<Map> {

    @Override
    public void close() {
    }

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public Map deserialize(String topic, byte[] message) {
        ByteArrayInputStream bais = new ByteArrayInputStream(message);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bais);
            Object o = in.readObject();
            if (o instanceof Map) {
                return (Map) o;
            } else
                return new HashMap<String, String>();
        } catch (ClassNotFoundException | IOException e) {
            log.error("Error:" + e.getMessage());
        } finally {
            try {
                bais.close();
            } catch (IOException ex) {
                log.info("ignore close exception");
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.info("ignore close exception");
            }
        }
        return new HashMap<String, String>();
    }

}
