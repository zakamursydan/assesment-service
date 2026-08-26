package com.techno.assesment.assesmentservice.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.boot.ApplicationRunner;
import org.apache.kafka.clients.admin.AdminClient;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.ssl.trust-store-location}")
    private String trustStoreLocation;

    @Value("${spring.kafka.ssl.trust-store-password}")
    private String trustStorePassword;

    @Value("${spring.kafka.ssl.key-store-location}")
    private String keyStoreLocation;

    @Value("${spring.kafka.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        
        try {
            properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, ResourceUtils.getFile(trustStoreLocation).getAbsolutePath());
            properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, ResourceUtils.getFile(keyStoreLocation).getAbsolutePath());
        } catch (FileNotFoundException e) {
            properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, trustStoreLocation);
            properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keyStoreLocation);
        }

        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, trustStorePassword);
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keyStorePassword);

        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ApplicationRunner kafkaConnectionTestRunner() {
        return args -> {
            try (AdminClient adminClient = AdminClient.create(producerFactory().getConfigurationProperties())) {
                String clusterId = adminClient.describeCluster().clusterId().get();
                log.info("✅ Berhasil terhubung ke Kafka! (Cluster ID: {})", clusterId);
            } catch (Exception e) {
                log.error("❌ Gagal terhubung ke Kafka: {}", e.getMessage());
            }
        };
    }
}
