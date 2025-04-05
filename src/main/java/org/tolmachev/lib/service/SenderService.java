package org.tolmachev.lib.service;

import org.tolmachev.lib.entity.LibrarySubscriptionEntity;

import java.util.Set;

public interface SenderService {
    void send(Set<LibrarySubscriptionEntity> subscriptionEntities);
}