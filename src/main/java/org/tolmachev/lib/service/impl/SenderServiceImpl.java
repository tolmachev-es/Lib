package org.tolmachev.lib.service.impl;

import org.springframework.stereotype.Service;
import org.tolmachev.lib.entity.LibrarySubscriptionEntity;
import org.tolmachev.lib.service.SenderService;

import java.util.Set;

@Service
public class SenderServiceImpl implements SenderService {
    @Override
    public void send(Set<LibrarySubscriptionEntity> subscriptionEntities) {

    }
}
