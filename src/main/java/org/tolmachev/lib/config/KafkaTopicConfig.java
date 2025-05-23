package org.tolmachev.lib.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic-name}")
    private String topicName;

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(topicName)
                           .partitions(1)
                           .replicas(1)
                           .build();
    }
}
