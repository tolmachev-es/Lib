package org.tolmachev.lib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolmachev.lib.entity.BookEntity;
import org.tolmachev.lib.entity.BookInSubscriptionEntity;
import org.tolmachev.lib.entity.LibrarySubscriptionEntity;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.repository.BookEntityRepository;
import org.tolmachev.lib.repository.BookInSubscriptionEntityRepository;
import org.tolmachev.lib.repository.LibrarySubscriptionEntityRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateDatabaseService {
    private final BookEntityRepository bookRepository;
    private final LibrarySubscriptionEntityRepository subscriptionRepository;
    private final BookInSubscriptionEntityRepository bookInSubscriptionRepository;

    public void updateSubscriptions(List<Data> dataList) {
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

        bookRepository.saveAll(books.values());
        subscriptionRepository.saveAll(subscriptions.values());
        bookInSubscriptionRepository.saveAll(bookInSubscriptions);
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
