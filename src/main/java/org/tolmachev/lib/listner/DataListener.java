package org.tolmachev.lib.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.service.SubscriptionService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataListener {

    private final SubscriptionService subscriptionService;

    @KafkaListener(topics = {"test-topic"}, groupId = "product", containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<Data> records) {
        log.debug("Получены данные по абонементам");
        subscriptionService.saveSubscriptions(records);
    }
}
