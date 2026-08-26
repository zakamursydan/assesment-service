package com.techno.assesment.assesmentservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        log.info("Mengirim pesan ke Kafka Topic [{}]: {}", topic, message);
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("✅ Pesan berhasil terkirim ke partisi [{}] dengan offset [{}]",
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("❌ Gagal mengirim pesan ke Kafka: {}", ex.getMessage());
                    }
                });
    }
}
