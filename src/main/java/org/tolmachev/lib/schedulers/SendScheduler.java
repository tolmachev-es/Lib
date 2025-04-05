package org.tolmachev.lib.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tolmachev.lib.entity.LibrarySubscriptionEntity;
import org.tolmachev.lib.repository.LibrarySubscriptionEntityRepository;
import org.tolmachev.lib.service.SenderService;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendScheduler {
    @Value("${scheduler.days}")
    private Integer days;
    private final SenderService senderService;
    private final LibrarySubscriptionEntityRepository subscriptionRepository;


    @Scheduled(cron = "${scheduler.cron}")
    public void runSenderService() {
        LocalDate now = LocalDate.now().minusDays(days);
        log.info("Началась отправка сообщений пользователям");
        Set<LibrarySubscriptionEntity> subscription = subscriptionRepository.findSubscription(now);
        log.info("Отправка закончена");
        senderService.send(subscription);
    }
}
