package org.tolmachev.lib.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.service.UpdateDatabaseService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataListener {

    private final UpdateDatabaseService updateDatabaseService;

    @KafkaListener(topics = {"test-topic"}, groupId = "product", containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<Data> records) {
        updateDatabaseService.updateSubscriptions(records);
    }
}
