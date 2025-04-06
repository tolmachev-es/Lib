package org.tolmachev.lib.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.service.SubscriptionService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataListener {

    private final SubscriptionService subscriptionService;

    @KafkaListener(topics = {"${kafka.topic-name}"}, groupId = "product", containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<Data> records, Acknowledgment ack) {
        log.debug("Получены данные по абонементам");
        subscriptionService.saveSubscriptions(records);
        ack.acknowledge();
        log.debug("Данные по абонементам обработаны");
    }
}
