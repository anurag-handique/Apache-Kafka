package com.kafka.enduser.config;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.kafka.enduser.config.AppConstants.GROUP_ID;

@Configuration
public class KafkaConfig {

    private final Logger LOG = LoggerFactory.getLogger(KafkaConfig.class);

    @KafkaListener(topics = AppConstants.LOCATION_UPDATE_TOPIC, groupId = GROUP_ID)
    public void updatedLocation(String value) {
        LOG.info("Received message: {}", value);
    }
}
