package org.tolmachev.lib.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tolmachev.lib.exceptions.SubscriptionNotFoundException;
import org.tolmachev.lib.mappers.SubscriptionMapper;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.model.Subscription;
import org.tolmachev.lib.model.UploadRequest;
import org.tolmachev.lib.repository.SubscriptionRepository;
import org.tolmachev.lib.service.LibraryService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    private final KafkaTemplate<String, Data> kafkaTemplate;
    private final SubscriptionRepository libraryRepository;
    private final SubscriptionMapper subscriptionMapper;
    @Value("${kafka.topic-name}")
    private String topicName;

    @Override
    public Subscription getSubscription(String userFullName) {
        log.debug("Получен запрос на поиск данных по пользователю {}", userFullName);
        return subscriptionMapper.map(libraryRepository.findLibrarySubscriptionEntityByFullNameIgnoreCase(userFullName)
                                                       .orElseThrow(() -> new SubscriptionNotFoundException(String.format("Пользователь с именем %s не найден", userFullName))));
    }

    @Override
    public void saveData(UploadRequest uploadRequest) {
        log.info("Началась отправка данных по абонементам пользователей");
        uploadRequest.getData().forEach(data ->  kafkaTemplate.send(topicName, UUID.randomUUID().toString(), data));
        log.info("Запись данных завершена, записана информация по {} абонементам", uploadRequest.getData().size());
    }
}
