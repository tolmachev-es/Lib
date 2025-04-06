package org.tolmachev.lib.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tolmachev.lib.entity.BookEntity;
import org.tolmachev.lib.entity.BookInSubscriptionEntity;
import org.tolmachev.lib.entity.LibrarySubscriptionEntity;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.repository.BookRepository;
import org.tolmachev.lib.repository.BookInSubscriptionRepository;
import org.tolmachev.lib.repository.SubscriptionRepository;
import org.tolmachev.lib.service.SubscriptionService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final BookRepository bookRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BookInSubscriptionRepository bookInSubscriptionRepository;

    @Transactional
    public void saveSubscriptions(List<Data> dataList) {
        log.debug("Началась обработка информации по абонементам");
        Map<String, BookEntity> books = new HashMap<>();
        Map<String, LibrarySubscriptionEntity> subscriptions = new HashMap<>();
        Set<BookInSubscriptionEntity> bookInSubscriptions = new HashSet<>();

        for (Data data : dataList) {
            BookEntity book;
            if (books.containsKey(data.getBookName())) {
                book = books.get(data.getBookName());
            } else {
                book = getBookEntity(data.getBookName(), data.getBookAuthor());
                books.put(book.getName(), book);
            }

            LibrarySubscriptionEntity subscription;
            if (subscriptions.containsKey(data.getUsername())) {
                subscription = subscriptions.get(data.getUsername());
            } else {
                subscription = getSubscriptionEntity(data.getUsername(), data.getUserFullName(), data.getUserActive());
                subscriptions.put(data.getUsername(), subscription);
            }
            bookInSubscriptions.add(subscription.addBook(book));
        }
        log.debug("Сущности созданы, начинается сохранение");

        bookRepository.saveAllAndFlush(books.values());
        subscriptionRepository.saveAllAndFlush(subscriptions.values());
        bookInSubscriptionRepository.saveAllAndFlush(bookInSubscriptions);
        log.debug("Сущности сохранены");
    }

    private BookEntity getBookEntity(String bookName, String author) {
        return bookRepository.findBookEntityByName(bookName)
                             .orElseGet(() -> BookEntity.builder()
                                                        .name(bookName)
                                                        .author(author)
                                                        .build());
    }

    private LibrarySubscriptionEntity getSubscriptionEntity(String username, String fullName, Boolean isActive) {
        return subscriptionRepository.findByUsername(username)
                                     .orElseGet(() -> LibrarySubscriptionEntity.builder()
                                                                               .username(username)
                                                                               .fullName(fullName)
                                                                               .active(isActive)
                                                                               .build());
    }
}
