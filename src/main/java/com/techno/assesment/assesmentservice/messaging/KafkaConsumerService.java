package com.techno.assesment.assesmentservice.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "assesment-topic", groupId = "${spring.kafka.consumer.group-id:assesment-group}")
    public void consumeMessage(String message) {
        log.info("📥 Menerima pesan dari Kafka Topic [assesment-topic]: {}", message);

    }
}
