package org.tolmachev.lib.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tolmachev.lib.exceptions.SubscriptionNotFoundException;
import org.tolmachev.lib.mappers.SubscriptionMapper;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.model.Subscription;
import org.tolmachev.lib.model.UploadRequest;
import org.tolmachev.lib.repository.LibrarySubscriptionEntityRepository;
import org.tolmachev.lib.service.LibraryService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final KafkaTemplate<String, Data> kafkaTemplate;
    private final LibrarySubscriptionEntityRepository libraryRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public Subscription getSubscription(String userFullName) {
        return subscriptionMapper.map(libraryRepository.findLibrarySubscriptionEntityByFullNameIgnoreCase(userFullName)
                                                       .orElseThrow(() -> new SubscriptionNotFoundException(String.format("Пользователь с именем %s не найден", userFullName))));
    }

    @Override
    public void saveOldData(UploadRequest uploadRequest) {
        for (Data data : uploadRequest.getData()) {
            String uuid = UUID.randomUUID().toString();
            kafkaTemplate.send("test-topic", uuid, data);
        }
    }
}
