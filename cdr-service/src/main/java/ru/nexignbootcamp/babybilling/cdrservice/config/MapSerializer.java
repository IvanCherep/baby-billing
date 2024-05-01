package ru.nexignbootcamp.babybilling.cdrservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Map;

@Slf4j
public class MapSerializer implements Serializer<Map<?, ?>> {

    @Override
    public void close() {
    }

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Map data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(data);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            log.error("Error:" + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                log.info("Ignore close exception");
            }
            try {
                baos.close();
            } catch (IOException ex) {
                log.info("Ignore close exception");
            }
        }

        return bytes;
    }

}
